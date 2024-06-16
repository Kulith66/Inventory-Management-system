package com.kulithdev.inventoryservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.kulithdev.inventoryservice.model.Product;

public interface ProductRepository extends MongoRepository<Product,String>{

}
