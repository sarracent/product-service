package com.klagan.productservice.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Object containing ServiceResponse")
public class ServiceResponse {
    @Schema(description = "Value indicating whether the server execution was successful. The value 0 is assumed to indicate that there is no processing error.")
    protected String resultCode;

    @Schema(description = "Description about the result of the service execution. It is a descriptive text associated with the resultCode.")
    protected String resultMessage;
}