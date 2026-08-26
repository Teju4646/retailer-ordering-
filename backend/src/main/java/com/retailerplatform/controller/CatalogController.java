package com.retailerplatform.controller;

import com.retailerplatform.domain.Product;
import com.retailerplatform.repo.ProductRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/catalog")
public class CatalogController {

    private final ProductRepository productRepo;

    public CatalogController(ProductRepository productRepo) {
        this.productRepo = productRepo;
    }

    @GetMapping("/products")
    public List<Product> listProducts() {
        return productRepo.findAll();
    }
}
