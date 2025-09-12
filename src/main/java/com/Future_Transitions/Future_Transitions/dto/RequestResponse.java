package com.Future_Transitions.Future_Transitions.dto;

import com.Future_Transitions.Future_Transitions.model.Role;
import com.Future_Transitions.Future_Transitions.model.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import java.util.List;
@JsonInclude(JsonInclude.Include.NON_NULL)

@Data
public class RequestResponse {

    private String name;
    private String surname;
    private String phoneNumber;
    private Role role;
    private String email;
    private String password;
    private String message;
    private int Error;
    private User user;
    private String token;
    private String refreshToken;
    private String expirationTime;
    private int statusCode;
    private List<User> UserList ;
    private List<String> roles;
    private String redirectUrl;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getError() {
        return Error;
    }

    public void setError(int error) {
        Error = error;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(String expirationTime) {
        this.expirationTime = expirationTime;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }


    public List<User> getUserList() {
        return UserList;
    }

    public void setUserList(List<User> userList) {
        UserList = userList;
    }
}
