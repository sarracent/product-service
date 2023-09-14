package com.klagan.productservice.controller.impl;


import com.klagan.productservice.controller.ProductController;
import com.klagan.productservice.model.bo.ProductDetail;
import com.klagan.productservice.service.ProductService;
import com.klagan.productservice.service.Resilience4jService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@AllArgsConstructor
public class ProductControllerImpl implements ProductController {
    private final ProductService productService;

    private final Resilience4jService resilience4jService;

    @Override
    public ResponseEntity<CompletableFuture<List<ProductDetail>>> getSimilarProducts(final String productId) {
        return ResponseEntity.ok(resilience4jService.executeProducts(() -> productService.getSimilarProducts(productId)));
    }
}
