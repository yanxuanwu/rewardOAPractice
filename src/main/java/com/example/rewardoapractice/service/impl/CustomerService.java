package com.example.rewardoapractice.service.impl;

import org.hibernate.Transaction;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface CustomerService {
    int calculateTotalRewardPoints(Long customerId);
    Map<String, Integer> calculateMonthlyRewardPoints(Long customerId);
}
