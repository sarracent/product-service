package com.klagan.productservice.service;

import com.klagan.productservice.model.bo.ProductDetail;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface ProductService {
    CompletableFuture<List<ProductDetail>> getSimilarProducts(String productId);
}
