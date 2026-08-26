package com.retailerplatform.controller;

import com.retailerplatform.domain.Franchise;
import com.retailerplatform.domain.Retailer;
import com.retailerplatform.repo.FranchiseRepository;
import com.retailerplatform.repo.RetailerRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/directory")
public class DirectoryController {

    private final RetailerRepository retailerRepo;
    private final FranchiseRepository franchiseRepo;

    public DirectoryController(RetailerRepository retailerRepo, FranchiseRepository franchiseRepo) {
        this.retailerRepo = retailerRepo;
        this.franchiseRepo = franchiseRepo;
    }

    @GetMapping("/retailers")
    public List<Retailer> listRetailers() {
        return retailerRepo.findAll();
    }

    @GetMapping("/franchises")
    public List<Franchise> listFranchises() {
        return franchiseRepo.findAll();
    }
}
