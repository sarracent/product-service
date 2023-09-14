package com.klagan.productservice.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@JsonInclude
@Schema(description = "Represents service responses generally HTTP error response-code (3xx, 4xx, 5xx) or business validations")
public class ErrorResponse extends ServiceResponse{
    private String message;
    private int status;
}
