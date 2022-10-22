package com.etrade.user.service;

import com.etrade.user.dto.LoginRequest;
import com.etrade.user.dto.LoginResponse;
import com.etrade.user.dto.RegisterRequest;

public interface UserService {
    public String test();

    LoginResponse login(LoginRequest loginRequest);

    boolean register(RegisterRequest registerRequest);
}
