package com.org.ticketing.support.service.impl;

import com.org.ticketing.support.model.User;
import com.org.ticketing.support.repository.UserRepository;
import com.org.ticketing.support.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

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
}
