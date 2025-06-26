package com.org.ticketing.support.service.impl;

import com.org.ticketing.support.dto.response.UserResponse;
import com.org.ticketing.support.mapper.UserMapper;
import com.org.ticketing.support.model.Role;
import com.org.ticketing.support.model.User;
import com.org.ticketing.support.repository.UserRepository;
import com.org.ticketing.support.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public boolean existsByEmail(String email) {
        return findByEmail(email).isPresent();
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public Page<UserResponse> getAllUsers(String role, Pageable pageable) {
        Page<User> users;

        if (role != null && !role.isBlank()) {
            try {
                Role userRole = Role.valueOf(role.toUpperCase());
                users = userRepository.findAllByRole(userRole, pageable);
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Invalid role: " + role);
            }
        } else {
            users = userRepository.findAll(pageable);
        }

        return users.map(userMapper::toDto);
    }
}
