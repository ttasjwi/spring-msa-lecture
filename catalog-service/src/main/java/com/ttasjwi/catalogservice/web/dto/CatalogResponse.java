package com.ttasjwi.catalogservice.web.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ttasjwi.catalogservice.service.dto.CatalogDto;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CatalogResponse {

    private String productId;
    private String productName;
    private Integer stock;
    private Integer unitPrice;
    private LocalDateTime createdAt;

    public CatalogResponse(CatalogDto dto) {
        this.productId = dto.getProductId();
        this.productName = dto.getProductName();
        this.stock = dto.getQuantity();
        this.unitPrice = dto.getUnitPrice();
        this.createdAt = dto.getCreatedAt();
    }
}
