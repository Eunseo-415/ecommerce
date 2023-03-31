package com.example.userapi.service.seller;

import com.example.userapi.domain.model.Seller;
import com.example.userapi.repository.SellerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SellerService {
    private final SellerRepository sellerRepository;

    public Optional<Seller> findByIdAndEmail(Long id, String email){
        return sellerRepository.findByIdAndEmail(id, email);
    }

    public Optional<Seller> findByValidSeller(String email, String password){
        return sellerRepository.findByEmailAndPasswordAndVerifyIsTrue(email, password);
    }
}
