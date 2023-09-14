package com.klagan.productservice.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Errors {
    ERROR_BUSINESS_GET_PRODUCTS_DETAIL("100000", "Error al obtener detalles del producto: %s"),

    ERROR_GENERAL("900000", "Error General -> [%s] %s");

    private final String code;
    private final String message;

}