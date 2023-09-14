package com.klagan.productservice.commons.resolver;


import com.klagan.productservice.constants.Errors;
import com.klagan.productservice.exception.CustomException;
import com.klagan.productservice.model.response.ServiceResponse;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import static com.klagan.productservice.constants.Errors.ERROR_BUSINESS_GET_PRODUCTS_DETAIL;

public class CustomExceptionResolverDelegate {

    private CustomExceptionResolverDelegate() {
    }

    public static ServiceResponse buildServiceResponse(Exception ex) {
        if (ex instanceof CustomException) {
            CustomException customException = (CustomException) ex;
            return ServiceResponse.builder()
                    .resultCode(customException.getCode())
                    .resultMessage(customException.getMessage())
                    .build();
        }
        else if (ex instanceof WebClientResponseException) {

            return ServiceResponse.builder()
                    .resultCode(ERROR_BUSINESS_GET_PRODUCTS_DETAIL.getCode())
                    .resultMessage(ERROR_BUSINESS_GET_PRODUCTS_DETAIL.getMessage())
                    .build();

        }
        else if (ex instanceof WebClientRequestException) {

            return ServiceResponse.builder()
                    .resultCode(ERROR_BUSINESS_GET_PRODUCTS_DETAIL.getCode())
                    .resultMessage(ERROR_BUSINESS_GET_PRODUCTS_DETAIL.getMessage())
                    .build();

        }
        else {
            return ServiceResponse.builder()
                    .resultCode(Errors.ERROR_GENERAL.getCode())
                    .resultMessage(String.format(Errors.ERROR_GENERAL.getMessage(), ex.getMessage(), ex))
                    .build();
        }
    }

}