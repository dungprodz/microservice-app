package com.example.orderservice.service.impl;

import com.example.orderservice.model.request.VerifyOrderRequest;
import com.example.orderservice.model.response.VerifyOrderResponse;
import com.example.orderservice.service.OrderService;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {
    @Override
    public VerifyOrderResponse verifyOrder(VerifyOrderRequest request) {
        return null;
    }
}
