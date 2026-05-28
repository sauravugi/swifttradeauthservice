package com.swifttrade.auth.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "st_department")
@Data
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "dept_id")
    private String id;

    @Column(name = "dept_name", nullable = false, unique = true)
    private String name;

    @Column(name = "dept_code", unique = true)
    private String code;

    @Column(name = "dept_active")
    private Boolean active;
}