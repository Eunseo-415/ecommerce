package com.example.orderapi.domain.product;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AddProductItemForm {
    private Long productId;
    private String name;
    private Integer price;
    private Integer count;
}
