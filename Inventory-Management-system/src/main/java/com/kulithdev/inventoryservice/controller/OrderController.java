package com.kulithdev.inventoryservice.controller;

import com.kulithdev.inventoryservice.dto.OrderRequest;
import com.kulithdev.inventoryservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public void placeOrder(@RequestBody OrderRequest orderRequest) {
        orderService.placeOrder(orderRequest);
    }
}
