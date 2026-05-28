package com.swifttrade.auth.core.model;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class Contact {

    private String name;

    private String email;

    private String mobileNumber;

    private String alternateMobileNumber;
}
