package br.com.ia.rag.embedding.infrastructure.web;

import br.com.ia.rag.embedding.application.usecase.CompareEmbeddingsUseCase;
import br.com.ia.rag.embedding.application.usecase.GenerateEmbeddingUseCase;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/embeddings")
public class EmbeddingController {

    private final GenerateEmbeddingUseCase generateEmbeddingUseCase;
    private final CompareEmbeddingsUseCase compareEmbeddingsUseCase;

    public EmbeddingController(
            GenerateEmbeddingUseCase generateEmbeddingUseCase,
            CompareEmbeddingsUseCase compareEmbeddingsUseCase
    ) {
        this.generateEmbeddingUseCase = generateEmbeddingUseCase;
        this.compareEmbeddingsUseCase = compareEmbeddingsUseCase;
    }

    @GetMapping
    public Map<String, Object> generate(
            @RequestParam String text
    ) {

        float[] embedding =
                generateEmbeddingUseCase.execute(text);

        return Map.of(
                "text", text,
                "dimensions", embedding.length,
                "embedding", embedding
        );
    }

    @GetMapping("/compare")
    public Map<String, Object> compare(
            @RequestParam String text1,
            @RequestParam String text2
    ) {

        double similarity =
                compareEmbeddingsUseCase.execute(text1, text2);

        return Map.of(
                "text1", text1,
                "text2", text2,
                "similarity", similarity
        );
    }
}
