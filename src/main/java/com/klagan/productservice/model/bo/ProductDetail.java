package com.klagan.productservice.model.bo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(hidden = true)
public class ProductDetail {
    private String id;
    private String name;
    private Double price;
    private Boolean availability;
}
