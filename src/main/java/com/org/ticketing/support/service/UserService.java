package com.org.ticketing.support.service;

import com.org.ticketing.support.dto.response.UserResponse;
import com.org.ticketing.support.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UserService {
    Optional<User> findByEmail(String email);
    User saveUser(User user);
    boolean existsByEmail(String email);
    Page<UserResponse> getAllUsers(String role, Pageable pageable);
}
