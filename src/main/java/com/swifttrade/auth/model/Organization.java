package com.swifttrade.auth.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "st_organization")
@Data
public class Organization {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String name;

    private String code;

    @Enumerated(EnumType.STRING)
    private OrganizationType type;

    private Boolean active;

    public enum OrganizationType {
        BANK,
        ADMIN,
        CORPORATE
    }
}