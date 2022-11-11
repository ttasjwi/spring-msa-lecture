package com.ttasjwi.catalogservice.service.dto;

import com.ttasjwi.catalogservice.domain.CatalogEntity;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CatalogDto {

    private String productId;
    private String productName;
    private Integer quantity;
    private Integer unitPrice;
    private Integer totalPrice;
    private LocalDateTime createdAt;

    private String orderId;
    private String userId;

    public CatalogDto(CatalogEntity catalogEntity) {
        this.productId = catalogEntity.getProductId();
        this.productName = catalogEntity.getProductName();
        this.quantity = catalogEntity.getStock();
        this.unitPrice = catalogEntity.getUnitPrice();
        this.createdAt = catalogEntity.getCreatedAt();
    }

}
