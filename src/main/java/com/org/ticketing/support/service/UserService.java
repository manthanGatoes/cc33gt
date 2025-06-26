package com.org.ticketing.support.service;

import com.org.ticketing.support.model.User;

import java.util.Optional;

public interface UserService {
    Optional<User> findByEmail(String email);
    User saveUser(User user);
    boolean existsByEmail(String email);
}
