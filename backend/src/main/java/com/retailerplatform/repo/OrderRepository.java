package com.retailerplatform.repo;

import com.retailerplatform.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, String> {
    List<Order> findByDirtyForCrmTrue();
}
