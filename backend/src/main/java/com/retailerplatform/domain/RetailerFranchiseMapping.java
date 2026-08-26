package com.retailerplatform.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class RetailerFranchiseMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String retailerId;
    private String franchiseId;
}
