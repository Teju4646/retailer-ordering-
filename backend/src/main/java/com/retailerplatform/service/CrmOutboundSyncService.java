package com.retailerplatform.service;

import com.retailerplatform.domain.Order;
import com.retailerplatform.repo.OrderRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CrmOutboundSyncService {

    private final OrderRepository orderRepo;

    public CrmOutboundSyncService(OrderRepository orderRepo) {
        this.orderRepo = orderRepo;
    }

    @Async
    public void enqueue(Order order) {
        syncToCrm(order);
    }

    private void syncToCrm(Order order) {
        if (order.getCrmExternalId() == null) {
            order.setCrmExternalId("CRM-" + UUID.randomUUID());
        }
        order.setDirtyForCrm(false);
        orderRepo.save(order);
    }

    @Scheduled(fixedDelay = 30000)
    public void retryFailedSyncs() {
        List<Order> pending = orderRepo.findByDirtyForCrmTrue();
        pending.forEach(this::syncToCrm);
    }
}
