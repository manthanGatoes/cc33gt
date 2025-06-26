package com.org.ticketing.support.repository;

import com.org.ticketing.support.model.Role;
import com.org.ticketing.support.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    List<User> findByRole(Role role);
    Page<User> findAllByRole(Role role, Pageable pageable);
}
