package com.kulithdev.inventoryservice.model;

import org.apache.el.stream.Optional;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(collection = "inventory")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Inventory {
    @Id
    private String id;
    private String skuCode;
    private Integer quantity;
    private String name;

}
