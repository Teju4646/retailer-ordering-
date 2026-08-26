package com.retailerplatform.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Franchise {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String name;
    private String region;

    @Enumerated(EnumType.STRING)
    private IndianState state;

    private String gstin;
}
