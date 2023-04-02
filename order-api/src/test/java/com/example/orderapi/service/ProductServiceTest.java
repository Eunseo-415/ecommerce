package com.example.orderapi.service;

import com.example.orderapi.domain.model.Product;
import com.example.orderapi.domain.product.AddProductForm;
import com.example.orderapi.domain.product.AddProductItemForm;
import com.example.orderapi.domain.product.UpdateProductForm;
import com.example.orderapi.domain.product.UpdateProductItemForm;
import com.example.orderapi.domain.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;


    @Test
    void addProduct() {
        Long sellerId = 1L;
        AddProductForm form = makeProductForm("에어팟 맥스", "헤드셋 입니다.", 3 );
        Product product = productService.addProduct(sellerId, form);
        Product result = productRepository.findWithProductItemsById(product.getId()).get();
        assertNotNull(result);
        assertEquals("에어팟 맥스", result.getName());
        assertEquals("헤드셋 입니다.", result.getDescription());
        assertEquals(3, result.getProductItems().size());
        assertEquals("에어팟 맥스"+0, result.getProductItems().get(0).getName());
        assertEquals(10000, result.getProductItems().get(0).getPrice());
        assertEquals(1, result.getProductItems().get(0).getCount());
    }

    private static AddProductForm makeProductForm(String name, String description, int itemCount){
        List<AddProductItemForm> itemForms = new ArrayList<>();
        for (int i = 0; i < itemCount; i++) {
            itemForms.add(makeProductItemForm(null, name + i));
        }
        return AddProductForm.builder()
                .name(name)
                .description(description)
                .items(itemForms)
                .build();
    }

    private static AddProductItemForm makeProductItemForm(Long id, String name){
        return AddProductItemForm.builder()
                .productId(id)
                .name(name)
                .price(10000)
                .count(1)
                .build();
    }

    private static UpdateProductForm updateProductForm(Long id, String name, String description, int itemCount){
        List<UpdateProductItemForm> updateProductItemForms = new ArrayList<>();
        for (int i = 0; i < itemCount; i++) {
            updateProductItemForms.add(updateProductItemForm((long) (i + 1), name + i, 100, 2));
        }
        return UpdateProductForm.builder()
                .id(id)
                .name(name)
                .description(description)
                .items(updateProductItemForms)
                .build();
    }

    private static UpdateProductItemForm updateProductItemForm(Long id, String name, Integer price, Integer count){
        return UpdateProductItemForm.builder()
                .id(id)
                .name(name)
                .price(price)
                .count(count)
                .build();
    }

    @Test
    void updateProduct() {
        Long sellerId = 1L;
        AddProductForm form = makeProductForm("에어팟 맥스", "헤드셋 입니다.", 3 );
        productService.addProduct(sellerId, form);
        UpdateProductForm updateProductForm = updateProductForm(1L, "+에어팟 맥스+","+헤드셋 입니다.+", 3);
        Product updated = productService.updateProduct(1L, updateProductForm);
        Product result = productRepository.findWithProductItemsById(updated.getId()).get();
        assertNotNull(result);
        assertEquals("+에어팟 맥스+", result.getName());
        assertEquals("+헤드셋 입니다.+", result.getDescription());
        assertEquals(3, result.getProductItems().size());
        assertEquals("+에어팟 맥스+"+0, result.getProductItems().get(0).getName());
        assertEquals(100, result.getProductItems().get(0).getPrice());
        assertEquals(2, result.getProductItems().get(0).getCount());
    }
}