package com.example.orderapi.application;

import com.example.orderapi.domain.model.Product;
import com.example.orderapi.domain.product.AddProductCartForm;
import com.example.orderapi.domain.product.AddProductForm;
import com.example.orderapi.domain.product.AddProductItemForm;
import com.example.orderapi.domain.redis.Cart;
import com.example.orderapi.domain.repository.ProductRepository;
import com.example.orderapi.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CartApplicationTest {

    @Autowired
    private CartApplication cartApplication;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @Test
    void ADD_TEST() {
        Product p = add_product();
        Product result = productRepository.findWithProductItemsById(p.getId()).get();
        assertNotNull(result);

        Cart cart = cartApplication.addCart(100L, makeForm(result));
        assertNotNull(cart);
        assertEquals(cart.getMessages().size(), 0);
    }

    @Test
    void ADD_TEST_MODIFY() {
        Long customerId = 100L;

        cartApplication.clearCart(customerId);
        Product p = add_product();
        Product result = productRepository.findWithProductItemsById(p.getId()).get();

        assertNotNull(result);


        Cart cart = cartApplication.addCart(customerId, makeForm(result));
        // 데이터가 잘 들어갔는지
        assertEquals(cart.getMessages().size(), 0);

        cart = cartApplication.getCart(customerId);
        assertEquals(cart.getMessages().size(), 1);
    }

    Product add_product(){
        Long sellerId = 1L;
        AddProductForm form = makeProductForm("에어팟 맥스", "헤드셋 입니다.", 3 );
        return productService.addProduct(sellerId, form);
    }

    AddProductCartForm makeForm(Product p){
        AddProductCartForm.ProductItem productItem = AddProductCartForm.ProductItem.builder()
                .id(p.getProductItems().get(0).getId())
                .name(p.getProductItems().get(0).getName())
                .count(p.getProductItems().get(0).getCount()-1)
                .price(p.getProductItems().get(0).getPrice())
                .build();
        return AddProductCartForm.builder()
                .id(p.getId())
                .sellerId(p.getSellerId())
                .description(p.getDescription())
                .name(p.getName())
                .items(List.of(productItem))
                .build();
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
                .count(10)
                .build();
    }

}