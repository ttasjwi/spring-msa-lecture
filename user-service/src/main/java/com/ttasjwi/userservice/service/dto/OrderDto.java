package com.ttasjwi.userservice.service.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class OrderDto {

    private String productId;
    private Integer quantity;
    private Integer unitPrice; // 단가
    private Integer totalPrice;
    private LocalDate createdAt;
    private String orderId;

}
