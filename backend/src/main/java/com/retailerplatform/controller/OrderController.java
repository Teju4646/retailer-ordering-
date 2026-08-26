package com.retailerplatform.controller;

import com.retailerplatform.domain.*;
import com.retailerplatform.repo.OrderRepository;
import com.retailerplatform.service.OrderLifecycleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderLifecycleService lifecycleService;
    private final OrderRepository orderRepo;

    public OrderController(OrderLifecycleService lifecycleService, OrderRepository orderRepo) {
        this.lifecycleService = lifecycleService;
        this.orderRepo = orderRepo;
    }

    @PostMapping
    public Order placeOrder(@RequestBody Order order) {
        return lifecycleService.placeOrder(order);
    }

    @PatchMapping("/{id}/status")
    public Order updateStatus(@PathVariable String id, @RequestParam OrderStatus status) {
        return lifecycleService.transition(id, status);
    }

    @GetMapping("/{id}")
    public Order getOrder(@PathVariable String id) {
        return orderRepo.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Order not found"));
    }

    @GetMapping("/franchise/{franchiseId}")
    public List<Order> getByFranchise(@PathVariable String franchiseId) {
        return orderRepo.findByFranchiseId(franchiseId);
    }

    @GetMapping("/retailer/{retailerId}")
    public List<Order> getByRetailer(@PathVariable String retailerId) {
        return orderRepo.findByRetailerId(retailerId);
    }
}
