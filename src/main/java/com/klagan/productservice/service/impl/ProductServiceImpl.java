package com.klagan.productservice.service.impl;

import com.klagan.productservice.model.bo.ProductDetail;
import com.klagan.productservice.model.bo.SimilarProductIds;
import com.klagan.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    @Autowired
    private WebClient webClient;

    @Override
    public CompletableFuture<List<ProductDetail>> fetchSimilarProducts(String productId) {
        return fetchSimilarProductIds(productId)
                .flatMapMany(Flux::fromIterable)
                .flatMap(this::fetchProductDetailById)
                .collectList()
                .toFuture();
    }

    private Mono<List<String>> fetchSimilarProductIds(String productId) {
        return webClient.get()
                .uri("/product/{productId}/similarids", productId)
                .retrieve()
                .bodyToMono(SimilarProductIds.class)
                .map(SimilarProductIds::getIds);
    }

    private Mono<ProductDetail> fetchProductDetailById(String productId) {
        return webClient.get()
                .uri("/product/{productId}", productId)
                .retrieve()
                .bodyToMono(ProductDetail.class);
    }

}
