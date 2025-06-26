package com.org.ticketing.support.dto.auth;

import com.org.ticketing.support.model.Role;
import lombok.Data;

@Data
public class RegisterRequest {
    private String name;
    private String email;
    private String password;
    private Role role; // CUSTOMER, AGENT, ADMIN
}
