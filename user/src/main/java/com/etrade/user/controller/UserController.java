package com.etrade.user.controller;

import com.etrade.core.result.*;
import com.etrade.user.dto.LoginRequest;
import com.etrade.user.dto.LoginResponse;
import com.etrade.user.dto.RegisterRequest;
import com.etrade.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(HttpServletRequest request, @RequestBody LoginRequest loginRequest){
        LoginResponse loginResponse = this.userService.login(loginRequest);
        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/register")
    public ResponseEntity<Result> register(HttpServletRequest request, @RequestBody RegisterRequest registerRequest){
        Result result = this.userService.register(registerRequest);
        return result.isSuccess() ? ResponseEntity.ok(result) : ResponseEntity.badRequest().body(result);
    }
}
