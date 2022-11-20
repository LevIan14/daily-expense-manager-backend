package com.example.DEM.service;

import com.example.DEM.entity.User;
import com.example.DEM.repository.SavedAmountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class SavedAmountService {
    @Autowired
    private SavedAmountRepository savedAmountRepository;

    public BigDecimal getSavedAmountUser() {
        String username = getUser();
        return this.savedAmountRepository.findByUserAmount_Username(username).getSavedAmount();
    }

    public String getUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            return authentication.getName();
        }
        return null;
    }
}
