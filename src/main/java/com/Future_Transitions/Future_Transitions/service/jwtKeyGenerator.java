package com.Future_Transitions.Future_Transitions.service;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Base64;

public class jwtKeyGenerator {

    public static void main(String[] args) {

        SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
        String base64Key = Base64.getEncoder().encodeToString(key.getEncoded());

        System.out.println("✅ Your JWT secret key (Base64 encoded):");
        System.out.println(base64Key);
    }
}

