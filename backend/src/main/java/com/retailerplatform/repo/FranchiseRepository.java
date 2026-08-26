package com.retailerplatform.repo;

import com.retailerplatform.domain.Franchise;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FranchiseRepository extends JpaRepository<Franchise, String> {
}
