package com.ttasjwi.orderservice.service;

import com.ttasjwi.orderservice.service.dto.OrderDto;

import java.util.List;

public interface OrderService {

    OrderDto createOrder(OrderDto orderDetails);
    OrderDto findOrderByOrderId(String orderId);
    List<OrderDto> findOrdersByUserId(String userId);

}
