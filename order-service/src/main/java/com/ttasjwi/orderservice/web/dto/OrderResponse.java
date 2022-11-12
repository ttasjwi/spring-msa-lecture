package com.ttasjwi.orderservice.web.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ttasjwi.orderservice.service.dto.OrderDto;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderResponse {

    private String productId;
    private Integer quantity;
    private Integer unitPrice;
    private Integer totalPrice;
    private String orderId;

    public OrderResponse(OrderDto orderDto) {
        this.productId = orderDto.getProductId();
        this.quantity = orderDto.getQuantity();
        this.unitPrice = orderDto.getUnitPrice();
        this.totalPrice = orderDto.getTotalPrice();
        this.orderId = orderDto.getOrderId();
    }
}
