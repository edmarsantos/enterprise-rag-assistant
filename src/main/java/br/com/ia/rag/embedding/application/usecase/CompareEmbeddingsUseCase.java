package br.com.ia.rag.embedding.application.usecase;

import org.springframework.stereotype.Service;

@Service
public class CompareEmbeddingsUseCase {

    private final GenerateEmbeddingUseCase generateEmbeddingUseCase;

    public CompareEmbeddingsUseCase(GenerateEmbeddingUseCase generateEmbeddingUseCase) {
        this.generateEmbeddingUseCase = generateEmbeddingUseCase;
    }

    public double execute(String text1, String text2) {

        float[] embedding1 = generateEmbeddingUseCase.execute(text1);
        float[] embedding2 = generateEmbeddingUseCase.execute(text2);

        return cosineSimilarity(embedding1, embedding2);
    }

    private static double cosineSimilarity(float[] a, float[] b) {

        if (a.length != b.length) {
            throw new IllegalStateException("Embeddings must have the same dimensions");
        }

        double dot = 0;
        double normA = 0;
        double normB = 0;

        for (int i = 0; i < a.length; i++) {
            dot += a[i] * b[i];
            normA += a[i] * a[i];
            normB += b[i] * b[i];
        }

        if (normA == 0 || normB == 0) {
            return 0;
        }

        return dot / (Math.sqrt(normA) * Math.sqrt(normB));
    }
}
