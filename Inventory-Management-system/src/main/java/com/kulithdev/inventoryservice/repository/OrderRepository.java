package com.kulithdev.inventoryservice.repository;

import com.kulithdev.inventoryservice.model.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderRepository extends MongoRepository<Order, String> {
}
