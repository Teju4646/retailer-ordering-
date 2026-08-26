package com.retailerplatform.service;

import com.retailerplatform.domain.*;
import com.retailerplatform.repo.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class OrderLifecycleService {

    private final OrderRepository orderRepo;
    private final RetailerRepository retailerRepo;
    private final FranchiseRepository franchiseRepo;
    private final ProductRepository productRepo;
    private final RetailerFranchiseMappingRepository mappingRepo;
    private final NotificationService notificationService;
    private final CrmOutboundSyncService crmOutboundSyncService;
    private final GstCalculationService gstService;

    private static final Map<OrderStatus, List<OrderStatus>> TRANSITIONS = Map.of(
        OrderStatus.PLACED,     List.of(OrderStatus.CONFIRMED, OrderStatus.CANCELLED),
        OrderStatus.CONFIRMED,  List.of(OrderStatus.DISPATCHED, OrderStatus.CANCELLED),
        OrderStatus.DISPATCHED, List.of(OrderStatus.DELIVERED),
        OrderStatus.DELIVERED,  List.of(),
        OrderStatus.CANCELLED,  List.of()
    );

    public OrderLifecycleService(OrderRepository orderRepo,
                                  RetailerRepository retailerRepo,
                                  FranchiseRepository franchiseRepo,
                                  ProductRepository productRepo,
                                  RetailerFranchiseMappingRepository mappingRepo,
                                  NotificationService notificationService,
                                  CrmOutboundSyncService crmOutboundSyncService,
                                  GstCalculationService gstService) {
        this.orderRepo = orderRepo;
        this.retailerRepo = retailerRepo;
        this.franchiseRepo = franchiseRepo;
        this.productRepo = productRepo;
        this.mappingRepo = mappingRepo;
        this.notificationService = notificationService;
        this.crmOutboundSyncService = crmOutboundSyncService;
        this.gstService = gstService;
    }

    public Order placeOrder(Order order) {
        Retailer retailer = retailerRepo.findById(order.getRetailerId())
            .orElseThrow(() -> new IllegalArgumentException("Retailer not found"));

        String franchiseId = mappingRepo.findByRetailerId(retailer.getId())
            .orElseThrow(() -> new IllegalStateException("No franchise mapped for retailer"))
            .getFranchiseId();
        Franchise franchise = franchiseRepo.findById(franchiseId)
            .orElseThrow(() -> new IllegalStateException("Franchise not found"));

        if (order.getLineItems() != null) {
            for (OrderLineItem line : order.getLineItems()) {
                Product product = productRepo.findBySku(line.getProductSku())
                    .orElseThrow(() -> new IllegalArgumentException("Unknown SKU: " + line.getProductSku()));

                double taxable = product.getUnitPrice() * line.getQuantity();
                var breakdown = gstService.calculate(retailer, franchise, taxable, product.getGstRatePct());

                line.setOrder(order);
                line.setUnitPrice(product.getUnitPrice());
                line.setHsnCode(product.getHsnCode());
                line.setTaxableValue(breakdown.taxableValue());
                line.setCgst(breakdown.cgst());
                line.setSgst(breakdown.sgst());
                line.setIgst(breakdown.igst());
                line.setLineTotal(breakdown.total());
            }
        }

        order.setFranchiseId(franchiseId);
        order.setStatus(OrderStatus.PLACED);
        order.setDirtyForCrm(true);

        Order saved = orderRepo.save(order);
        notificationService.notifyStatusChange(saved);
        crmOutboundSyncService.enqueue(saved);
        return saved;
    }

    public Order transition(String orderId, OrderStatus target) {
        Order order = orderRepo.findById(orderId)
            .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        List<OrderStatus> allowed = TRANSITIONS.get(order.getStatus());
        if (!allowed.contains(target)) {
            throw new IllegalStateException(
                "Cannot move from " + order.getStatus() + " to " + target);
        }

        order.setStatus(target);
        order.setDirtyForCrm(true);
        Order saved = orderRepo.save(order);

        notificationService.notifyStatusChange(saved);
        crmOutboundSyncService.enqueue(saved);
        return saved;
    }
}
