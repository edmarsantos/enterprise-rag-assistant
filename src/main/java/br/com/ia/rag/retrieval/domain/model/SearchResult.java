package br.com.ia.rag.retrieval.domain.model;

public record SearchResult(
        Long id,
        String content,
        double similarity
) {
}
