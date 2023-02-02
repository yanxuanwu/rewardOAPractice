package com.example.rewardoapractice.controller;

import com.example.rewardoapractice.service.impl.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class customerController {
    private final CustomerService customerService;

    @Autowired
    public customerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/totalRewards/{id}")
    public int getTotalRewardsPoints(@PathVariable Long customerId){
        return customerService.calculateTotalRewardPoints(customerId);
    }

    @GetMapping("/monthlyRewards/{id}")
    public Map<String, Integer> getMonthlyRewardPoints(@PathVariable Long customerId) {
        return customerService.calculateMonthlyRewardPoints(customerId);
    }
}
