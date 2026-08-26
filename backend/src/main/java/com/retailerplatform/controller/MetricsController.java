package com.retailerplatform.controller;

import com.retailerplatform.repo.OrderLineItemRepository;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/metrics")
public class MetricsController {

    private final OrderLineItemRepository lineItemRepo;

    public MetricsController(OrderLineItemRepository lineItemRepo) {
        this.lineItemRepo = lineItemRepo;
    }

    @GetMapping("/summary")
    public Map<String, Object> summary() {
        return Map.of(
            "totalPurchases", lineItemRepo.countDistinctOrders(),
            "unitsSold", lineItemRepo.sumQuantity(),
            "topSales", lineItemRepo.topSellingProducts(5),
            "marketSharePct", lineItemRepo.marketSharePerProduct()
        );
    }
}
