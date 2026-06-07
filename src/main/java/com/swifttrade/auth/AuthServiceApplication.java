package com.swifttrade.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AuthServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthServiceApplication.class, args);
        System.out.println(
                new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder()
                        .encode("1234")
        );
	}

}
