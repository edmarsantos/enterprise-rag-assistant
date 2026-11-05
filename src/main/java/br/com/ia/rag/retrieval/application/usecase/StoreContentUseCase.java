package br.com.ia.rag.retrieval.application.usecase;

import br.com.ia.rag.embedding.application.port.out.EmbeddingPort;
import br.com.ia.rag.retrieval.application.port.out.VectorStorePort;
import org.springframework.stereotype.Service;

@Service
public class StoreContentUseCase {

    private final EmbeddingPort embeddingPort;
    private final VectorStorePort vectorStorePort;

    public StoreContentUseCase(
            EmbeddingPort embeddingPort,
            VectorStorePort vectorStorePort
    ) {
        this.embeddingPort = embeddingPort;
        this.vectorStorePort = vectorStorePort;
    }

    public Long execute(String content) {

        if (content == null || content.isBlank()) {
            throw new IllegalArgumentException(
                    "Content must not be blank"
            );
        }

        float[] embedding =
                embeddingPort.generate(content);

        return vectorStorePort.save(
                content,
                embedding
        );
    }

}
