package com.example.DEM.controller;

import com.example.DEM.entity.User;
import com.example.DEM.service.SavedAmountService;
import com.example.DEM.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/saved-amount")
public class SavedAmountController {
    @Autowired
    private SavedAmountService savedAmountService;

    @GetMapping("/total")
    public BigDecimal getSavedAmountUser(){
        return savedAmountService.getSavedAmountUser();
    }
}
