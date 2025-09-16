package com.Future_Transitions.Future_Transitions.service;

import org.springframework.security.core.userdetails.UserDetails;


public interface JWTService {

    String extractUserName(String token);

    String generateToken(UserDetails userDetails);
    boolean isTokenValid(String token , UserDetails userDetails);
}
