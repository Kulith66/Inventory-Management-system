package com.kulithdev.inventoryservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Document(collection = "orderLineItems")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderLineItems {
    @Id
    private String id;
    private String skuCode;
    private BigDecimal price;
    private Integer quantity;
}
