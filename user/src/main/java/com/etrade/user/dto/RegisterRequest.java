package com.etrade.user.dto;

import lombok.Data;

@Data
public class RegisterRequest {
    private String userName;
    private String email;
    private String password;
    private String firstname;
    private String lastName;
}
