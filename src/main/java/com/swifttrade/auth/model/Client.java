package com.swifttrade.auth.model;

import com.swifttrade.auth.core.model.Address;
import com.swifttrade.auth.core.model.Contact;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "st_client")
@Data
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "client_id")
    private String id;

    @Column(name = "client_name", nullable = false, unique = true)
    private String name;

    @Column(name = "client_code", nullable = false, unique = true)
    private String code;

    @Column(name = "client_active")
    private Boolean active;

    @Column(name = "client_swift_code")
    private String swiftCode;

    @Column(name = "client_bic_code")
    private String bicCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_org_id")
    private Organization organization;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "name", column = @Column(name = "client_contact_name")),
            @AttributeOverride(name = "email", column = @Column(name = "client_contact_email")),
            @AttributeOverride(name = "mobileNumber", column = @Column(name = "client_contact_number")),
            @AttributeOverride(name = "alternateMobileNumber", column = @Column(name = "client_alt_contact_number"))
    })
    private Contact contact;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "line1", column = @Column(name = "client_address_line_1")),
            @AttributeOverride(name = "line2", column = @Column(name = "client_address_line_2")),
            @AttributeOverride(name = "city", column = @Column(name = "client_city")),
            @AttributeOverride(name = "state", column = @Column(name = "client_state")),
            @AttributeOverride(name = "country", column = @Column(name = "client_country")),
            @AttributeOverride(name = "pincode", column = @Column(name = "client_pincode"))
    })
    private Address address;
}