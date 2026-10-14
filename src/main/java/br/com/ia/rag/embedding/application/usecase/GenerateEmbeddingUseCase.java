package br.com.ia.rag.embedding.application.usecase;

import br.com.ia.rag.embedding.application.port.out.EmbeddingPort;
import org.springframework.stereotype.Service;

@Service
public class GenerateEmbeddingUseCase {

    private final EmbeddingPort embeddingPort;

    public GenerateEmbeddingUseCase(EmbeddingPort embeddingPort) {
        this.embeddingPort = embeddingPort;
    }

    public float[] execute(String text) {

        if (text == null || text.isBlank()) {
            throw new IllegalArgumentException("Text must not be blank");
        }

        return embeddingPort.generate(text);
    }
}

