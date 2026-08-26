package com.retailerplatform.service;

import com.retailerplatform.domain.Order;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    public void notifyStatusChange(Order order) {
        System.out.printf("Order %s status changed to %s%n", order.getId(), order.getStatus());
    }
}
