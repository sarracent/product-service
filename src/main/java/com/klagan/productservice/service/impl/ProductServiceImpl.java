package com.klagan.productservice.service.impl;

import com.klagan.productservice.model.bo.ProductDetail;
import com.klagan.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    @Value("${similar.ids-url}")
    private String similarIdsUrl;
    @Value("${product.id-url}")
    private String productIdUrl;

    @Autowired
    private WebClient webClient;

    @Override
    @Async("threadPoolExecutor")
    public CompletableFuture<List<ProductDetail>> getSimilarProducts(String productId) {
        List<String> similarIds = webClient.get()
                .uri(similarIdsUrl, productId)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<String>>() {})
                .block();

        List<CompletableFuture<ProductDetail>> futures = similarIds.stream()
                .map(this::getProductDetail).toList();

        return CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
                .thenApply(v -> futures.stream()
                        .map(CompletableFuture::join)
                        .collect(Collectors.toList()));
    }

    @Async("threadPoolExecutor")
    public CompletableFuture<ProductDetail> getProductDetail(String id) {
        return webClient.get()
                .uri(productIdUrl, id)
                .retrieve()
                .bodyToMono(ProductDetail.class)
                .toFuture();
    }

}
