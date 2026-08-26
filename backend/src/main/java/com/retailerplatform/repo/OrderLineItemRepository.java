package com.retailerplatform.repo;

import com.retailerplatform.domain.OrderLineItem;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public interface OrderLineItemRepository extends JpaRepository<OrderLineItem, String> {

    @Query("SELECT COUNT(DISTINCT l.order.id) FROM OrderLineItem l")
    long countDistinctOrders();

    @Query("SELECT COALESCE(SUM(l.quantity), 0) FROM OrderLineItem l")
    long sumQuantity();

    @Query("SELECT l.productSku, SUM(l.quantity) as totalQty FROM OrderLineItem l GROUP BY l.productSku ORDER BY totalQty DESC")
    List<Object[]> topSellingProductsRaw(Pageable pageable);

    default List<Map<String, Object>> topSellingProducts(int limit) {
        List<Object[]> raw = topSellingProductsRaw(PageRequest.of(0, limit));
        List<Map<String, Object>> result = new ArrayList<>();
        for (Object[] row : raw) {
            Map<String, Object> m = new LinkedHashMap<>();
            m.put("sku", row[0]);
            m.put("unitsSold", row[1]);
            result.add(m);
        }
        return result;
    }

    @Query("SELECT l.productSku, SUM(l.quantity) FROM OrderLineItem l GROUP BY l.productSku")
    List<Object[]> quantityByProductRaw();

    default List<Map<String, Object>> marketSharePerProduct() {
        List<Object[]> raw = quantityByProductRaw();
        long total = raw.stream().mapToLong(r -> ((Number) r[1]).longValue()).sum();
        List<Map<String, Object>> result = new ArrayList<>();
        for (Object[] row : raw) {
            Map<String, Object> m = new LinkedHashMap<>();
            m.put("sku", row[0]);
            double pct = total == 0 ? 0 : (((Number) row[1]).doubleValue() / total) * 100.0;
            m.put("sharePct", Math.round(pct * 10.0) / 10.0);
            result.add(m);
        }
        return result;
    }
}
