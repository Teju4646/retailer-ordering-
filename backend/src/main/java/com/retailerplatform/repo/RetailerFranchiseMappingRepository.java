package com.retailerplatform.repo;

import com.retailerplatform.domain.RetailerFranchiseMapping;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RetailerFranchiseMappingRepository extends JpaRepository<RetailerFranchiseMapping, String> {
    Optional<RetailerFranchiseMapping> findByRetailerId(String retailerId);
}
