package com.kulithdev.inventoryservice.controller;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import com.kulithdev.inventoryservice.service.ProductService;
import com.kulithdev.inventoryservice.dto.ProductResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

import com.kulithdev.inventoryservice.dto.ProductRequest;

@RestController
@RequestMapping("api/product")
@RequiredArgsConstructor
public class ProductController {

    @Autowired
    private ProductService productService;

    // CRAETE PRODUCT
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createProduct(@RequestBody ProductRequest productRequest) {
        productService.createProduct(productRequest);

    }

    // GET ALL PRODUCT
    @GetMapping
    @ResponseStatus
    public List<ProductResponse> getAllProducts() {
        return productService.getAllProducts();
    }

}
