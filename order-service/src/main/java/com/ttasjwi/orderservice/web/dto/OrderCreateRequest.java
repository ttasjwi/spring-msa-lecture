package com.ttasjwi.orderservice.web.dto;

import com.ttasjwi.orderservice.service.dto.OrderDto;
import lombok.Data;

@Data
public class OrderCreateRequest {

    private String productId;
    private Integer unitPrice;
    private Integer quantity;

    public OrderDto toDto() {
        return OrderDto.builder()
                .productId(productId)
                .quantity(quantity)
                .unitPrice(unitPrice)
                .build();
    }

}
