package com.etrade.user.dto;

import com.etrade.core.model.helpers.Address;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class RegisterRequest {
    private String userName;
    private String email;
    private String password;
    private String firstname;
    private String lastName;
    private LocalDate birthDate;
    private String gender;
    private Address address;
    private List<String> prefers;
}
