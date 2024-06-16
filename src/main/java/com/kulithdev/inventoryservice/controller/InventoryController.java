package com.kulithdev.inventoryservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.kulithdev.inventoryservice.service.InventoryService;
import com.kulithdev.inventoryservice.dto.InventoryRequest; // Ensure correct package import
import com.kulithdev.inventoryservice.dto.InventoryResponse;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.List;
import lombok.RequiredArgsConstructor;



@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;
    //CHECK STOCK
 
    //CHECK STOCK AND GET DETAILS
    @GetMapping("/details/{skuCode}")
    public ResponseEntity<InventoryResponse> getInventoryDetails(@PathVariable String skuCode) {
        InventoryResponse response = inventoryService.getInventoryDetailsIfInStock(skuCode);
        if (response != null) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    //Oder INVentory
    @PostMapping("/order")
    @ResponseStatus(HttpStatus.CREATED)
    public void orderInventory(@RequestBody InventoryRequest inventoryRequest) {
        inventoryService.orderInventory(inventoryRequest);
    }
    
    //CREATE INVENTORY
    @PostMapping("/createInventory")
    @ResponseStatus(HttpStatus.CREATED)
    public void createInventory(@RequestBody InventoryRequest inventoryRequest) {
        inventoryService.createOrUpdateInventory(inventoryRequest);
    }
    
    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryResponse> getAllInventory() {
        return inventoryService.getAllInventory();
    }
    //getsingleitem
    
    @GetMapping("/{skuCode}")
    @ResponseStatus(HttpStatus.OK)
    public InventoryResponse getInventoryItem(@PathVariable("skuCode") String skuCode) {
        return inventoryService.getInventoryItem(skuCode);
    }
    //DELATE INVEONTORY
    @DeleteMapping("/{skuCode}")
    public ResponseEntity<Void> deleteInventory(@PathVariable String skuCode) {
        inventoryService.deleteInventory(skuCode);
        return ResponseEntity.noContent().build();
    }
      @PutMapping("/{skuCode}")
    public ResponseEntity<Void> updateInventory(@PathVariable String skuCode, @RequestBody InventoryRequest inventoryRequest) {
        inventoryService.updateInventory(skuCode, inventoryRequest);
        return ResponseEntity.noContent().build();
    }

    
}
