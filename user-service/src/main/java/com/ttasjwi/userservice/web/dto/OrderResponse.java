package com.ttasjwi.userservice.web.dto;

import com.ttasjwi.userservice.service.dto.OrderDto;
import lombok.Data;

import java.time.LocalDate;

@Data
public class OrderResponse {

    private String productId;
    private Integer quantity;
    private Integer unitPrice; // 단가
    private Integer totalPrice;
    private LocalDate createdAt;
    private String orderId;

    public OrderResponse(OrderDto orderDto) {
        this.productId = orderDto.getProductId();
        this.quantity = orderDto.getQuantity();
        this.unitPrice = orderDto.getUnitPrice();
        this.totalPrice = orderDto.getTotalPrice();
        this.createdAt = orderDto.getCreatedAt();
        this.orderId = orderDto.getOrderId();
    }
}
