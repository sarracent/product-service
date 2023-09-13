package com.klagan.productservice.service;

import java.util.function.Supplier;

public interface Resilience4jService {
    <T> T executeProducts(Supplier<T> operation);
}
