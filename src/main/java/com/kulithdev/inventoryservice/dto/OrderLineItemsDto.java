package com.kulithdev.inventoryservice.dto;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderLineItemsDto {
    private String id;
    private String skuCode;
    private BigDecimal price;
    private Integer quantity;
}
