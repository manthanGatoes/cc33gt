package com.org.ticketing.support.dto.response;

import com.org.ticketing.support.model.Role;
import lombok.Data;

@Data
public class UserResponse {
    private Long id;
    private String name;
    private String email;
    private Role role;
}
