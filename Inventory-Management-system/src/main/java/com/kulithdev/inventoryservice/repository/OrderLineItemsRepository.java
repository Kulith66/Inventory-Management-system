package com.kulithdev.inventoryservice.repository;

import com.kulithdev.inventoryservice.model.OrderLineItems;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderLineItemsRepository extends MongoRepository<OrderLineItems, String> {
}
