package com.klagan.productservice.service.impl;


import com.klagan.productservice.service.Resilience4jService;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.stereotype.Service;

import java.util.function.Supplier;

@Service
public class Resilience4jServiceImpl implements Resilience4jService {

    private static final String PRODUCTS_API = "products";

    @Override
    @CircuitBreaker(name = PRODUCTS_API)
    @RateLimiter(name = PRODUCTS_API)
    @Bulkhead(name = PRODUCTS_API)
    @Retry(name = PRODUCTS_API)
    public <T> T executeProducts(Supplier<T> operation) {
        return operation.get();
    }


}
