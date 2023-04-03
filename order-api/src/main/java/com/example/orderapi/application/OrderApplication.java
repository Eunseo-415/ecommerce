package com.example.orderapi.application;

import com.example.orderapi.client.UserClient;
import com.example.orderapi.client.user.ChangeBalanceForm;
import com.example.orderapi.client.user.CustomerDto;
import com.example.orderapi.domain.model.ProductItem;
import com.example.orderapi.domain.redis.Cart;
import com.example.orderapi.exception.CustomException;
import com.example.orderapi.exception.ErrorCode;
import com.example.orderapi.service.ProductItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class OrderApplication {
    private final CartApplication cartApplication;
    private final UserClient userClient;
    private final ProductItemService productItemService;

    @Transactional
    public void order(String token, Cart cart){
        Cart orderCart = cartApplication.refreshCart(cart);
        if (orderCart.getMessages().size()>0){
            throw new CustomException(ErrorCode.ORDER_FAIL_CHECK_CART);
        }

        CustomerDto customerDto = userClient.getCustomerInfo(token).getBody();
        int totalPrice = getTotalPrice(cart);
        if (customerDto.getBalance() < getTotalPrice(cart)){
            throw new CustomException(ErrorCode.ORDER_FAIL_NO_MONEY);
        }
        userClient.changeBalance(token, ChangeBalanceForm.builder()
                        .from("USER")
                        .message("ORDER")
                        .money(-totalPrice)
                        .build());

        for (Cart.Product product : orderCart.getProducts()){
            for (Cart.ProductItem cartItem: product.getItems()){
                ProductItem productItem = productItemService.getProductItem(cartItem.getId());
                productItem.setCount(productItem.getCount() - cartItem.getCount());
            }
        }
    }

    private Integer getTotalPrice(Cart cart){
        return cart.getProducts().stream().flatMapToInt(product ->
                product.getItems().stream().flatMapToInt(productItem -> IntStream.of(productItem.getPrice()*productItem.getCount())))
                .sum();
    }
}
