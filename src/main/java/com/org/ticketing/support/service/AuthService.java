package com.org.ticketing.support.service;

import com.org.ticketing.support.dto.auth.AuthResponse;
import com.org.ticketing.support.dto.auth.LoginRequest;
import com.org.ticketing.support.dto.auth.RegisterRequest;

public interface AuthService {
    AuthResponse register(RegisterRequest request);
    AuthResponse login(LoginRequest request);
}
