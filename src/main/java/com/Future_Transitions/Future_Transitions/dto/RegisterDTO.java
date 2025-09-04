package com.Future_Transitions.Future_Transitions.dto;

import com.Future_Transitions.Future_Transitions.model.Role;
import com.Future_Transitions.Future_Transitions.model.User;
import lombok.Data;
import lombok.RequiredArgsConstructor;


@Data
@RequiredArgsConstructor
public class RegisterDTO {

    private String username;
    private String name;
    private String surname;
    private String address;
    private String passwordhashed;
    private String email;
    private int phoneNumber;
    private String password;
    private Role role;
    private User user;
    private String message;
}

