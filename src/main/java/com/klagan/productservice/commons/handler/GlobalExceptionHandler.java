package com.klagan.productservice.commons.handler;


import com.klagan.productservice.commons.aop.LogAspect;
import com.klagan.productservice.commons.resolver.CustomExceptionResolverDelegate;
import com.klagan.productservice.exception.impl.*;
import com.klagan.productservice.model.response.ServiceResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({BusinessException.class, WebClientResponseException.class, WebClientRequestException.class})
    public ResponseEntity<ServiceResponse> handleBusinessExceptions(Exception exception, WebRequest request) {
        final var serviceResponse = CustomExceptionResolverDelegate.buildServiceResponse(exception);
        LogAspect.logFinishOperationInError(serviceResponse);
        return ResponseEntity.ok(serviceResponse);
    }

}