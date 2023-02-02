package com.example.rewardoapractice.service.impl;

import com.example.rewardoapractice.exception.CustomerNotFoundException;
import com.example.rewardoapractice.pojo.entity.Customer;
import com.example.rewardoapractice.pojo.entity.ShoppingTransaction;
import com.example.rewardoapractice.repository.CustomerRepository;
import com.example.rewardoapractice.repository.CustomerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

@Service
public class CustomerServiceImpl implements CustomerService{
    private final CustomerRepository customerRepository;
    private static final Logger logger = Logger.getLogger(CustomerServiceImpl.class.getName());

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public int calculateTotalRewardPoints(Long customerId) {
        Customer customer = customerRepository.getOne(customerId);
        if (customer == null) {
            logger.log(Level.INFO, "no customer found");
            throw new EntityNotFoundException();
        }
        List<ShoppingTransaction> transactionList = customer.getShoppingTransactions();
        int totalRewardsPoint = 0;
        for (ShoppingTransaction tran : transactionList) {
            totalRewardsPoint += tran.getRewardsPoint();
        }
        return totalRewardsPoint;
    }

    @Override
    public Map<String, Integer> calculateMonthlyRewardPoints(Long customerId) {
        Customer customer = customerRepository.getOne(customerId);
        if (customer == null) {
            logger.log(Level.INFO, "no customer found");
            throw new EntityNotFoundException();
        }
        List<ShoppingTransaction> transactionList = customer.getShoppingTransactions();
        Date currentDate = new Date();
        Map<String, Integer> monthlyRewards = new HashMap<>();

        for (ShoppingTransaction tran: transactionList){
            long diffInMillies = Math.abs(currentDate.getTime() - tran.getDate().getTime());
            long diffDays = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
            if (diffDays > 90) {
                continue;
            }
            String month = String.valueOf(tran.getDate().getMonth());
            if (monthlyRewards.containsKey(month)) {
                monthlyRewards.put(month, monthlyRewards.get(month) + tran.getRewardsPoint());
            }
            else {
                monthlyRewards.put(month, tran.getRewardsPoint());
            }
        }

        return monthlyRewards;
    }
}
