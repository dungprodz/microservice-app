package com.example.orderservice.service;

import com.example.orderservice.model.request.VerifyOrderRequest;
import com.example.orderservice.model.response.VerifyOrderResponse;

public interface OrderService {
    VerifyOrderResponse verifyOrder(VerifyOrderRequest request);
}
