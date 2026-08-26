package com.retailerplatform.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Retailer {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String name;
    private String city;

    @Enumerated(EnumType.STRING)
    private IndianState state;

    @Pattern(regexp = "\\d{2}[A-Z]{5}\\d{4}[A-Z]{1}\\d[Z]{1}[A-Z\\d]{1}",
              message = "Invalid GSTIN format")
    private String gstin;
}
