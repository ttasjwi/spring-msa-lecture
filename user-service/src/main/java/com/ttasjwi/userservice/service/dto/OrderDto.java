package com.ttasjwi.userservice.service.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderDto {

    private String productId;
    private Integer quantity;
    private Integer unitPrice; // 단가
    private Integer totalPrice;
    private LocalDateTime createdAt;
    private String orderId;

}
