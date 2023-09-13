package com.klagan.productservice.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.klagan.productservice.model.bo.ProductDetail;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Object containing Product Detail Response")
public class ProductDetailResponse extends ServiceResponse {
    @Schema(description = "List of Products Details")
    private List<ProductDetail> parametersList;
}
