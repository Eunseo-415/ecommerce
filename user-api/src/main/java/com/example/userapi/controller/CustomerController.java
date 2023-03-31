package com.example.userapi.controller;

import com.example.domain.config.JwtAuthenticationProvider;
import com.example.domain.domain.common.UserVo;
import com.example.userapi.domain.customer.ChangeBalanceForm;
import com.example.userapi.domain.customer.CustomerDto;
import com.example.userapi.domain.model.Customer;
import com.example.userapi.exception.CustomException;
import com.example.userapi.exception.ErrorCode;
import com.example.userapi.service.customer.CustomerBalanceService;
import com.example.userapi.service.customer.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {
    private final JwtAuthenticationProvider provider;
    private final CustomerService customerService;
    private final CustomerBalanceService customerBalanceService;

    @GetMapping("/getInfo")
    public ResponseEntity<CustomerDto> getInfo(@RequestHeader(name = "X-AUTH-TOKEN") String token){
        UserVo userVo = provider.getUserVo(token);
        Customer customer = customerService.findByIdAndEmail(userVo.getId(), userVo.getEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        return ResponseEntity.ok(CustomerDto.from(customer));
    }

    @PostMapping("/balance")
    public ResponseEntity<Integer> changeBalance(@RequestHeader(name = "X-AUTH-TOKEN") String token,
                                                 @RequestBody ChangeBalanceForm form){
        UserVo userVo = provider.getUserVo(token);
        return ResponseEntity.ok(customerBalanceService.changeBalance(userVo.getId(), form).getCurrentMoney());
    }

}
