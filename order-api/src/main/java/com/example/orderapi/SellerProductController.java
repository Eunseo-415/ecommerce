package com.example.orderapi;

import com.example.domain.config.JwtAuthenticationProvider;
import com.example.orderapi.domain.product.*;
import com.example.orderapi.service.ProductItemService;
import com.example.orderapi.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/seller/product")
@RequiredArgsConstructor
public class SellerProductController {

    private final ProductService productService;
    private final ProductItemService productItemService;
    private final JwtAuthenticationProvider provider;

    @PostMapping
    public ResponseEntity<ProductDto> addProduct(@RequestHeader(name = "X-AUTH-TOKEN") String token, @RequestBody AddProductForm addProductForm){
        return  ResponseEntity.ok(ProductDto.from(productService.addProduct(provider.getUserVo(token).getId(), addProductForm)));
    }

    @PostMapping("/item")
    public ResponseEntity<ProductDto> addProductItem(@RequestHeader(name = "X-AUTH-TOKEN") String token, @RequestBody AddProductItemForm addProductItemForm){
        return  ResponseEntity.ok(ProductDto.from(productItemService.addProductItem(provider.getUserVo(token).getId(), addProductItemForm)));
    }

    @PutMapping
    public ResponseEntity<ProductDto> updateProduct(@RequestHeader(name = "X-AUTH-TOKEN") String token, @RequestBody UpdateProductForm updateProductForm){
        return  ResponseEntity.ok(ProductDto.from(productService.updateProduct(provider.getUserVo(token).getId(), updateProductForm)));
    }

    @PutMapping("/item")
    public ResponseEntity<ProductItemDto> updateProductItem(@RequestHeader(name = "X-AUTH-TOKEN") String token, @RequestBody UpdateProductItemForm updateProductItemForm){
        return  ResponseEntity.ok(ProductItemDto.from(productItemService.updateProductItem(provider.getUserVo(token).getId(), updateProductItemForm)));
    }
}
