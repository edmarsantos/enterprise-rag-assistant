package br.com.ia.rag.embedding.infrastructure.ai;

import br.com.ia.rag.embedding.application.port.out.EmbeddingPort;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.stereotype.Component;

@Component
public class SpringAiEmbeddingAdapter implements EmbeddingPort  {

    private final EmbeddingModel embeddingModel;

    public SpringAiEmbeddingAdapter(EmbeddingModel embeddingModel) {
        this.embeddingModel = embeddingModel;
    }

    @Override
    public float[] generate(String text) {
        return embeddingModel.embed(text);
    }

}
