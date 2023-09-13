package com.klagan.productservice.service;

import com.klagan.productservice.model.bo.ProductDetail;
import org.springframework.scheduling.annotation.Async;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface ProductService {
    @Async
    CompletableFuture<List<ProductDetail>> fetchSimilarProducts(String productId);
}
