package com.Future_Transitions.Future_Transitions.dto;

import com.Future_Transitions.Future_Transitions.model.Role;
import com.Future_Transitions.Future_Transitions.model.User;
import lombok.Data;

import java.util.List;
@Data
public class RequestResponse {


    private String name;
    private String surname;
    private String phoneNumber;
    private Role role;
    private String email;
    private String password;
    private String message;
    private int StatusCode;
    private int Error;
    private User user;
    private String token;
    private String refreshToken;
    private String expirationTime;

    private List<User> UserList ;
}
