package com.klagan.productservice.controller;

import com.klagan.productservice.model.bo.ProductDetail;
import com.klagan.productservice.service.ProductService;
import com.klagan.productservice.service.Resilience4jService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private Resilience4jService resilience4jService;

    @GetMapping("/{productId}/similar")
    public ResponseEntity<CompletableFuture<List<ProductDetail>>> getSimilarProducts(@PathVariable String productId) {
        return ResponseEntity.ok(resilience4jService.executeProducts(() -> productService.fetchSimilarProducts(productId)));
    }
}
