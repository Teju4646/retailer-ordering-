package com.retailerplatform.repo;

import com.retailerplatform.domain.Retailer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RetailerRepository extends JpaRepository<Retailer, String> {
}
