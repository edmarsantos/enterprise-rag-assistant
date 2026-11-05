package br.com.ia.rag.retrieval.infrastructure.persistence;

import br.com.ia.rag.retrieval.application.port.out.VectorStorePort;

import br.com.ia.rag.retrieval.domain.model.SearchResult;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PgVectorAdapter implements VectorStorePort {
    private static final int EMBEDDING_DIMENSIONS = 1024;

    private final JdbcTemplate jdbcTemplate;

    public PgVectorAdapter(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Long save(
            String content,
            float[] embedding
    ) {

        validateEmbedding(embedding);

        String sql = """
                INSERT INTO document_chunks (
                    content,
                    embedding
                )
                VALUES (?, CAST(? AS vector))
                RETURNING id
                """;

        return jdbcTemplate.queryForObject(
                sql,
                Long.class,
                content,
                toVectorLiteral(embedding)
        );
    }

    @Override
    public List<SearchResult> search(
            float[] queryEmbedding,
            int limit
    ) {

        validateEmbedding(queryEmbedding);

        String sql = """
                WITH query_vector AS (
                    SELECT CAST(? AS vector) AS embedding
                )
                SELECT
                    dc.id,
                    dc.content,
                    1 - (dc.embedding <=> qv.embedding) AS similarity
                FROM document_chunks dc
                CROSS JOIN query_vector qv
                ORDER BY dc.embedding <=> qv.embedding
                LIMIT ?
                """;

        return jdbcTemplate.query(
                sql,
                (resultSet, rowNum) ->
                        new SearchResult(
                                resultSet.getLong("id"),
                                resultSet.getString("content"),
                                resultSet.getDouble("similarity")
                        ),
                toVectorLiteral(queryEmbedding),
                limit
        );
    }

    private void validateEmbedding(float[] embedding) {

        if (embedding == null) {
            throw new IllegalArgumentException(
                    "Embedding must not be null"
            );
        }

        if (embedding.length != EMBEDDING_DIMENSIONS) {
            throw new IllegalArgumentException(
                    "Embedding must have "
                            + EMBEDDING_DIMENSIONS
                            + " dimensions"
            );
        }
    }

    private String toVectorLiteral(float[] embedding) {

        StringBuilder builder = new StringBuilder("[");

        for (int i = 0; i < embedding.length; i++) {

            if (i > 0) {
                builder.append(",");
            }

            builder.append(embedding[i]);
        }

        builder.append("]");

        return builder.toString();
    }
}