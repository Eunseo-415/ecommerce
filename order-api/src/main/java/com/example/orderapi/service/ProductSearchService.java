package com.example.orderapi.service;

import com.example.orderapi.domain.model.Product;
import com.example.orderapi.domain.repository.ProductRepository;
import com.example.orderapi.exception.CustomException;
import com.example.orderapi.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductSearchService {
    private final ProductRepository productRepository;

    public List<Product> searchByName(String name){
        return productRepository.searchByName(name);
    }

    public Product getByProductId(Long productId){
        return productRepository.findWithProductItemsById(productId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_PRODUCT));
    }

//    public List<Product> getListByProductIds(List<Long> productIds){
//        return productRepository.findAllById(productIds);
//    }

    public List<Product> getListByProductIds(List<Long> productIds){
        return productRepository.findAllByIdIn(productIds);
    }
}
