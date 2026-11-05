package br.com.ia.rag.retrieval.application.port.out;

import br.com.ia.rag.retrieval.domain.model.SearchResult;

import java.util.List;

public interface VectorStorePort {

    Long save(
            String content,
            float[] embedding
    );

    List<SearchResult> search(
            float[] queryEmbedding,
            int limit
    );
}
