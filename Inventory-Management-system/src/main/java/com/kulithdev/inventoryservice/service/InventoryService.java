package com.kulithdev.inventoryservice.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kulithdev.inventoryservice.model.Inventory;
import com.kulithdev.inventoryservice.repository.InventoryRepository;
import com.kulithdev.inventoryservice.dto.InventoryRequest;
import com.kulithdev.inventoryservice.dto.InventoryResponse;
import com.kulithdev.inventoryservice.exception.InventoryNotFoundException;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    // Check if SKU code is in stock

    // Get inventory details if SKU code is in stock
    public InventoryResponse getInventoryDetailsIfInStock(String skuCode) {
        Optional<Inventory> inventoryOptional = inventoryRepository.findBySkuCode(skuCode);
        if (inventoryOptional.isPresent()) {
            Inventory inventory = inventoryOptional.get();
            if (inventory.getQuantity() > 0) {
                return InventoryResponse.builder()
                        .skuCode(inventory.getSkuCode())
                        .quantity(inventory.getQuantity())
                        .name(inventory.getName())
                        // Add other fields if needed
                        .build();
            }
        }
        return null;
    }

    // Create or update inventory
    public synchronized void createOrUpdateInventory(InventoryRequest inventoryRequest) {
        String skuCode = inventoryRequest.getSkuCode();
        log.info("Processing inventory request for SKU code {}: Requested quantity = {}",
                skuCode, inventoryRequest.getQuantity());

        Optional<Inventory> existingInventoryOptional = inventoryRepository.findBySkuCode(skuCode);
        if (existingInventoryOptional.isPresent()) {
            Inventory existingInventory = existingInventoryOptional.get();

            int newQuantity = existingInventory.getQuantity() + inventoryRequest.getQuantity();
            existingInventory.setQuantity(newQuantity);
            inventoryRepository.save(existingInventory);

            log.info("Updated quantity of product with SKU code {}: New quantity = {}",
                    skuCode, newQuantity);
        } else {
            Inventory newInventory = Inventory.builder()
                    .skuCode(skuCode)
                    .quantity(inventoryRequest.getQuantity())
                    .name(inventoryRequest.getName()) // Assuming InventoryRequest has a getName() method
                    .build();
            inventoryRepository.save(newInventory);

            log.info("Product with SKU code {} is saved with quantity {}",
                    skuCode, newInventory.getQuantity());
        }

    }

    // Delete inventory by SKU code
    public void deleteInventory(String skuCode) {
        Optional<Inventory> inventoryOptional = inventoryRepository.findBySkuCode(skuCode);
        inventoryOptional.ifPresent(inventory -> {
            inventoryRepository.delete(inventory);
            log.info("Product with SKU code {} is deleted", skuCode);
        });
    }

    // Update inventory by SKU code
    public void updateInventory(String skuCode, InventoryRequest inventoryRequest) {
        Optional<Inventory> inventoryOptional = inventoryRepository.findBySkuCode(skuCode);
        inventoryOptional.ifPresent(inventory -> {
            inventory.setQuantity(inventoryRequest.getQuantity());
            inventory.setName(inventoryRequest.getName());
            inventoryRepository.save(inventory);
            log.info("Product with SKU code {} is updated", skuCode);
        });
    }
    // getsingle inventory items

    public InventoryResponse getInventoryItem(String skuCode) {
        Optional<Inventory> inventoryOptional = inventoryRepository.findBySkuCode(skuCode);

        if (inventoryOptional.isPresent()) {
            Inventory inventory = inventoryOptional.get();
            return convertToInventoryResponse(inventory);
        } else {
            throw new InventoryNotFoundException ("Inventory item with SKU code " + skuCode + " not found");
        }
    }

    private InventoryResponse convertToInventoryResponse(Inventory inventory) {
        return new InventoryResponse(inventory.getSkuCode(), inventory.getQuantity(), inventory.getName());
    }

    // Get all inventory items
    public List<InventoryResponse> getAllInventory() {
        List<Inventory> inventoryList = inventoryRepository.findAll();
        return inventoryList.stream()
                .map(this::mapToInventoryResponse)
                .collect(Collectors.toList());
    }

    // Map Inventory to InventoryResponse
    private InventoryResponse mapToInventoryResponse(Inventory inventory) {
        return InventoryResponse.builder()
                .skuCode(inventory.getSkuCode())
                .quantity(inventory.getQuantity())
                .name(inventory.getName())
                // Add other fields if needed
                .build();
    }

    // order
    public synchronized void orderInventory(InventoryRequest inventoryRequest) {
        String skuCode = inventoryRequest.getSkuCode();
        int requestedQuantity = inventoryRequest.getQuantity();
        String productName = inventoryRequest.getName(); // Assuming InventoryRequest has a getName() method

        log.info("Processing inventory request for SKU code {}: Requested quantity = {}", skuCode, requestedQuantity);

        Optional<Inventory> existingInventoryOptional = inventoryRepository.findBySkuCode(skuCode);
        if (existingInventoryOptional.isPresent()) {
            Inventory existingInventory = existingInventoryOptional.get();
            int currentQuantity = existingInventory.getQuantity();

            if (currentQuantity >= requestedQuantity) {
                int newQuantity = currentQuantity - requestedQuantity;
                existingInventory.setQuantity(newQuantity);
                inventoryRepository.save(existingInventory);

                log.info("Successfully ordered {} items of {} - {}. New quantity: {}",
                        requestedQuantity, skuCode, productName, newQuantity);
            } else {
                log.warn("Insufficient inventory for SKU code {}: Requested = {}, Available = {}",
                        skuCode, requestedQuantity, currentQuantity);
            }
        } else {
            log.warn("Product with SKU code {} not found", skuCode);
        }
    }

}
