package com.kulithdev.inventoryservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "order")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Order {
    @Id
    private String id;
    private String orderNumber;
    @DBRef
    private List<OrderLineItems> orderLineItemsList;
}
