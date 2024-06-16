package com.kulithdev.inventoryservice.repository;

import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import com.kulithdev.inventoryservice.model.Inventory;

public interface InventoryRepository extends MongoRepository<Inventory, String> {
    Optional<Inventory> findBySkuCode(String skuCode);
}
