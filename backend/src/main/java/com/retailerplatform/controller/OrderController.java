package com.retailerplatform.controller;

import com.retailerplatform.domain.*;
import com.retailerplatform.service.OrderLifecycleService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderLifecycleService lifecycleService;

    public OrderController(OrderLifecycleService lifecycleService) {
        this.lifecycleService = lifecycleService;
    }

    @PostMapping
    public Order placeOrder(@RequestBody Order order) {
        return lifecycleService.placeOrder(order);
    }

    @PatchMapping("/{id}/status")
    public Order updateStatus(@PathVariable String id, @RequestParam OrderStatus status) {
        return lifecycleService.transition(id, status);
    }
}
