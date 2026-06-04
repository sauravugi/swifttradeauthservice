package com.swifttrade.auth.model;

import com.swifttrade.auth.core.model.Address;
import com.swifttrade.auth.core.model.Contact;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "st_user")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "user_id")
    private String id;

    @Column(name = "user_first_name", nullable = false)
    private String firstName;

    @Column(name = "user_last_name")
    private String lastName;

    @Column(name = "user_email", nullable = false, unique = true)
    private String email;

    @Column(name = "user_username", nullable = false, unique = true)
    private String userName;

    @Column(name = "user_password", nullable = false)
    private String password;

    @Column(name = "user_employee_id")
    private String employeeId;

    @Column(name = "user_active")
    private Boolean active;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(
            name = "st_user_roles",
            joinColumns = @JoinColumn(name = "user_id")
    )
    @Column(name = "role")
    private Set<Role> roles;

    @Column(name = "user_last_login_at")
    private LocalDateTime lastLoginAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_client_id")
    private Client client;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "st_user_department",
            joinColumns = @JoinColumn(name = "user_dept_user_id"),
            inverseJoinColumns = @JoinColumn(name = "user_dept_dept_id")
    )
    private Set<Department> departments;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "name", column = @Column(name = "user_contact_name")),
            @AttributeOverride(name = "email", column = @Column(name = "user_contact_email")),
            @AttributeOverride(name = "mobileNumber", column = @Column(name = "user_contact_number")),
            @AttributeOverride(name = "alternateMobileNumber", column = @Column(name = "user_alt_contact_number"))
    })
    private Contact contact;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "line1", column = @Column(name = "user_address_line_1")),
            @AttributeOverride(name = "line2", column = @Column(name = "user_address_line_2")),
            @AttributeOverride(name = "city", column = @Column(name = "user_city")),
            @AttributeOverride(name = "state", column = @Column(name = "user_state")),
            @AttributeOverride(name = "country", column = @Column(name = "user_country")),
            @AttributeOverride(name = "pincode", column = @Column(name = "user_pincode"))
    })
    private Address address;

    public enum Role {
        SUPER_ADMIN,
        BANK_ADMIN,
        BANK_USER,
        CORPORATE_ADMIN,
        CORPORATE_USER,
        CHECKER,
        MAKER,
        VIEWER
    }
}