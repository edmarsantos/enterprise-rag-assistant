package br.com.ia.rag.retrieval.application.usecase;

import br.com.ia.rag.embedding.application.port.out.EmbeddingPort;
import br.com.ia.rag.retrieval.application.port.out.VectorStorePort;
import br.com.ia.rag.retrieval.domain.model.SearchResult;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchSimilarContentUseCase {

    private final EmbeddingPort embeddingPort;
    private final VectorStorePort vectorStorePort;

    public SearchSimilarContentUseCase(
            EmbeddingPort embeddingPort,
            VectorStorePort vectorStorePort
    ) {
        this.embeddingPort = embeddingPort;
        this.vectorStorePort = vectorStorePort;
    }

    public List<SearchResult> execute(
            String query,
            int limit
    ) {

        if (query == null || query.isBlank()) {
            throw new IllegalArgumentException(
                    "Query must not be blank"
            );
        }

        if (limit <= 0) {
            throw new IllegalArgumentException(
                    "Limit must be greater than zero"
            );
        }

        float[] embedding =
                embeddingPort.generate(query);

        return vectorStorePort.search(
                embedding,
                limit
        );
    }
}
