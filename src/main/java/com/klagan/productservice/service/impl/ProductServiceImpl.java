package com.klagan.productservice.service.impl;

import com.klagan.productservice.annotations.log.LogService;
import com.klagan.productservice.exception.impl.BusinessException;
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

import static com.klagan.productservice.constants.Errors.ERROR_BUSINESS_GET_PRODUCTS_DETAIL;

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
    @LogService
    @Async("threadPoolExecutor")
    public CompletableFuture<List<ProductDetail>> getSimilarProducts(String productId) {
       try {
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
       } catch (Exception e) {
           throw new BusinessException(ERROR_BUSINESS_GET_PRODUCTS_DETAIL.getCode(), String.format(ERROR_BUSINESS_GET_PRODUCTS_DETAIL.getMessage(), productId));
       }

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
