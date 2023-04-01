package com.example.orderapi.domain.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

//builder no,all argsconstructor 은 테스트 용 -> mocking 사용시 필요 없음
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddProductForm {

    private String name;
    private String description;
    private List<AddProductItemForm> items;
}
