package com.example.orderapi.service;

import com.example.orderapi.client.RedisClient;
import com.example.orderapi.domain.product.AddProductCartForm;
import com.example.orderapi.domain.redis.Cart;
import com.example.orderapi.domain.redis.Cart.ProductItem;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class CartService {
    private final RedisClient redisClient;

    public Cart getCart(Long customerId){
        return redisClient.get(customerId, Cart.class);
    }

    public Cart addCart(Long customerId, AddProductCartForm form){
        Cart cart = redisClient.get(customerId, Cart.class);

        if (cart == null){
            cart = new Cart();
            cart.setCustomerId(customerId);
        }

        Optional<Cart.Product> productOptional = cart.getProducts().stream()
                .filter(product -> product.getId().equals(form.getId())).findFirst();

        if (productOptional.isPresent()){
            Cart.Product redisProduct = productOptional.get();

            List<Cart.ProductItem> items = form.getItems().stream()
                    .map(Cart.ProductItem::from).collect(Collectors.toList());

            Map<Long, ProductItem> redisItemMap = redisProduct.getItems().stream()
                    .collect(Collectors.toMap(Cart.ProductItem::getId, it-> it));

            if (!redisProduct.getName().equals(form.getName())){
                cart.addMessage(redisProduct.getName()+"의 정보가 변경되었습니다. 확인 부탁드립니다.");
            }

            for (Cart.ProductItem item: items){
                Cart.ProductItem redisItem = redisItemMap.get(item.getId());

                if(redisItem == null){
                    redisProduct.getItems().add(item);
                } else {
                    if (!redisItem.getPrice().equals(item.getPrice())){
                        cart.addMessage(redisProduct.getName()+"의 정보가 변경되었습니다. 확인 부탁드립니다.");
                    }
                    redisItem.setCount(redisItem.getCount()+item.getCount());
                }
            }
        } else {
            Cart.Product product = Cart.Product.from(form);
            cart.getProducts().add(product);
        }
        redisClient.put(customerId, cart);
        return cart;

    }

}
