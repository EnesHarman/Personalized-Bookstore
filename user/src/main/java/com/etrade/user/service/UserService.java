package com.etrade.user.service;

import com.etrade.core.result.*;
import com.etrade.user.dto.LoginRequest;
import com.etrade.user.dto.LoginResponse;
import com.etrade.user.dto.RegisterRequest;

public interface UserService {

    LoginResponse login(LoginRequest loginRequest);

    Result register(RegisterRequest registerRequest);
}
