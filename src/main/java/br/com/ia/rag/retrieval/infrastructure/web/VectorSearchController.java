package br.com.ia.rag.retrieval.infrastructure.web;

import br.com.ia.rag.retrieval.application.usecase.SearchSimilarContentUseCase;
import br.com.ia.rag.retrieval.application.usecase.StoreContentUseCase;
import br.com.ia.rag.retrieval.domain.model.SearchResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/vector-store")
public class VectorSearchController {

    private final StoreContentUseCase storeContentUseCase;
    private final SearchSimilarContentUseCase searchSimilarContentUseCase;

    public VectorSearchController(
            StoreContentUseCase storeContentUseCase,
            SearchSimilarContentUseCase searchSimilarContentUseCase
    ) {
        this.storeContentUseCase = storeContentUseCase;
        this.searchSimilarContentUseCase =
                searchSimilarContentUseCase;
    }

    @PostMapping
    public Map<String, Object> store(
            @RequestBody StoreContentRequest request
    ) {

        Long id =
                storeContentUseCase.execute(
                        request.content()
                );

        return Map.of(
                "id", id,
                "content", request.content()
        );
    }

    @GetMapping("/search")
    public List<SearchResult> search(
            @RequestParam String query,
            @RequestParam(defaultValue = "5") int limit
    ) {

        return searchSimilarContentUseCase.execute(
                query,
                limit
        );
    }

    public record StoreContentRequest(
            String content
    ) {
    }
}
