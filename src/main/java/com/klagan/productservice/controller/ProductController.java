package com.klagan.productservice.controller;

import com.klagan.productservice.model.bo.ProductDetail;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Tag(name = "Product Controller", description = "The Product Api")
@Validated
@RequestMapping(path = "/product")
public interface ProductController {
    @GetMapping(value = "/{productId}/similar")
    ResponseEntity<CompletableFuture<List<ProductDetail>>> getSimilarProducts(@NotBlank @PathVariable String productId);

}
