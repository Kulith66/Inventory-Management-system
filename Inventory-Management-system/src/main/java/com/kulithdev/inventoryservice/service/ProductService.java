package com.kulithdev.inventoryservice.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kulithdev.inventoryservice.dto.ProductRequest;
import com.kulithdev.inventoryservice.dto.ProductResponse;
import com.kulithdev.inventoryservice.model.Product;
import com.kulithdev.inventoryservice.repository.ProductRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;

    // create product
    public void createProduct(ProductRequest productRequest) {
        Product product = Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .quantity(productRequest.getQuantity())
                .build();
        productRepository.save(product);
        log.info("product {} is saved", product.getId());
    }

    // get ALL products
    public List<ProductResponse> getAllProducts() {
        List<Product> products = productRepository.findAll();

        return products.stream().map(this::mapToProductResponse).toList();
    }

    private ProductResponse mapToProductResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .quantity((product.getQuantity()))
                .build();
    }
}
