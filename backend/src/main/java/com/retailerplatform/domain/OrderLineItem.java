package com.retailerplatform.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class OrderLineItem {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    @JsonBackReference
    private Order order;

    private String productSku;
    private String serialNumber;
    private int quantity;
    private double unitPrice;
    private String hsnCode;

    private double taxableValue;
    private double cgst;
    private double sgst;
    private double igst;
    private double lineTotal;
}
