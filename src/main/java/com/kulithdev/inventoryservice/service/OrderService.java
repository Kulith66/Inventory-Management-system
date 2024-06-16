package com.kulithdev.inventoryservice.service;

import com.kulithdev.inventoryservice.dto.OrderLineItemsDto;
import com.kulithdev.inventoryservice.dto.OrderRequest;
import com.kulithdev.inventoryservice.model.Order;
import com.kulithdev.inventoryservice.model.OrderLineItems;
import com.kulithdev.inventoryservice.repository.OrderLineItemsRepository;
import com.kulithdev.inventoryservice.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderLineItemsRepository orderLineItemsRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository, OrderLineItemsRepository orderLineItemsRepository) {
        this.orderRepository = orderRepository;
        this.orderLineItemsRepository = orderLineItemsRepository;
    }

    @Transactional
    public void placeOrder(OrderRequest orderRequest) {
        validateOrderRequest(orderRequest);

        List<OrderLineItems> orderLineItemsList = orderRequest.getOrderLineItemsDtoList().stream()
                .map(this::mapToOrderLineItems)
                .collect(Collectors.toList());

        // Save OrderLineItems first to ensure IDs are generated
        orderLineItemsList = orderLineItemsRepository.saveAll(orderLineItemsList);

        // Create Order and set its orderLineItems
        Order order = new Order();
        order.setOrderNumber(generateOrderNumber());
        order.setOrderLineItemsList(orderLineItemsList);

        orderRepository.save(order);
        System.out.println("Order ID: " );
    }

    private OrderLineItems mapToOrderLineItems(OrderLineItemsDto dto) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setSkuCode(dto.getSkuCode());
        orderLineItems.setPrice(dto.getPrice());
        orderLineItems.setQuantity(dto.getQuantity());
        return orderLineItems;
    }

    private String generateOrderNumber() {
        return java.util.UUID.randomUUID().toString();
    }

    private void validateOrderRequest(OrderRequest orderRequest) {
        if (orderRequest == null || orderRequest.getOrderLineItemsDtoList() == null ||
            orderRequest.getOrderLineItemsDtoList().isEmpty()) {
            throw new IllegalArgumentException("Invalid order request");
        }

        // Additional validations can be added here (e.g., check if SKUs exist, etc.)
    }
}
