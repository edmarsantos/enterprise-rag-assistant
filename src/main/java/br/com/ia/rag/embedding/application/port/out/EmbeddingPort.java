package br.com.ia.rag.embedding.application.port.out;

public interface EmbeddingPort {

    float[] generate(String text);

}
