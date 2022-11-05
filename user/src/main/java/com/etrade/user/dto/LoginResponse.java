package com.etrade.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    private String access_token;
    private String refresh_token;
    private String expires_in;
    private String refresh_expires_in;
    private String token_type;
    private String role;

    public LoginResponse(String access_token, String refresh_token, String expires_in, String refresh_expires_in, String token_type) {
        this.access_token = access_token;
        this.refresh_token = refresh_token;
        this.expires_in = expires_in;
        this.refresh_expires_in = refresh_expires_in;
        this.token_type = token_type;
    }
}

