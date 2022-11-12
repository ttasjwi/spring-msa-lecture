package com.ttasjwi.orderservice.service.dto;

import com.ttasjwi.orderservice.domain.OrderEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;

@Data
public class OrderDto {

    private String productId;
    private Integer quantity;
    private Integer unitPrice;
    private Integer totalPrice;

    private String orderId;
    private String userId;

    public OrderDto(OrderEntity orderEntity) {
        this.productId = orderEntity.getProductId();
        this.quantity = orderEntity.getQuantity();
        this.unitPrice = orderEntity.getUnitPrice();
        this.totalPrice = orderEntity.getTotalPrice();
        this.orderId = orderEntity.getOrderId();
        this.userId = orderEntity.getUserId();
    }

    @Builder(access = AccessLevel.PUBLIC)
    private OrderDto(String productId, Integer quantity, Integer unitPrice, Integer totalPrice, String orderId, String userId) {
        this.productId = productId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.totalPrice = totalPrice;
        this.orderId = orderId;
        this.userId = userId;
    }

    public OrderEntity toEntity() {
        return OrderEntity.builder()
                .productId(productId)
                .quantity(quantity)
                .unitPrice(unitPrice)
                .orderId(orderId)
                .userId(userId)
                .build();
    }

}
