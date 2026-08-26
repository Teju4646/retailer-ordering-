package com.retailerplatform.controller;

import com.retailerplatform.domain.Product;
import com.retailerplatform.repo.ProductRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/crm/inbound")
public class CrmInboundSyncController {

    private final ProductRepository productRepo;

    public CrmInboundSyncController(ProductRepository productRepo) {
        this.productRepo = productRepo;
    }

    @PostMapping("/catalog-change")
    public void handleCatalogChange(@RequestBody List<Product> changedProducts) {
        changedProducts.forEach(productRepo::save);
    }

    @PostMapping("/backfill")
    public String triggerBulkBackfill() {
        return "Backfill complete (demo): products mirrored";
    }
}
