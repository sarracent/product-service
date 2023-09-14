package com.klagan.productservice.exception;

import java.util.Collections;
import java.util.List;

public interface CustomException {
    String getCode();

    String getMessage();

    default ExceptionType getExceptionType() {
        return ExceptionType.CUSTOM_EXCEPTION;
    }

    default List<Object> getExtraInfo() {
        return Collections.emptyList();
    }


}
