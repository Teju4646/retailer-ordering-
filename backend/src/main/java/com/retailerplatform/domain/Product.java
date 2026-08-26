package com.retailerplatform.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String name;
    private String sku;

    @Column(name = "hsn_code")
    private String hsnCode;

    private double unitPrice;
    private double gstRatePct;
}
