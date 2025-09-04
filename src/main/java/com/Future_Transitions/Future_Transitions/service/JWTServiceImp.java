package com.Future_Transitions.Future_Transitions.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

@Component
public class JWTServiceImp implements JWTService {

    private static final long EXPIRATION_TIME = 86400000; // 24 hours
    private final SecretKey key;

    public JWTServiceImp() {
        byte[] keyBytes = Decoders.BASE64.decode("Nkd52ChnpGlnqEVxxrgSoGlLrcShEEBY");
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }


    public String generateToken(UserDetails userDetails){
        return Jwts.builder().setSubject(userDetails.getUsername())
                .setIssuedAt((new Date(System.currentTimeMillis())))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                .signWith(getSignKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    public  String generateRefreshToken(HashMap<String, Object> claims, UserDetails userDetails){
        long refreshExpirationTime = 1000L * 60 * 60 * 24 * 7; // 7 days, adjust as needed

        return Jwts.builder()
                .claims(claims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + refreshExpirationTime))
                .signWith(key , SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractUserName(String token){
        return extractClaim(token, Claims::getSubject);
        }
        private <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = extractAllClaim(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaim(String token) {
        return Jwts.parser().setSigningKey(getSignKey()).build().parseClaimsJws(token).getBody();
    }

    private Key getSignKey() {
        byte[] key = Decoders.BASE64.decode("Nkd52ChnpGlnqEVxxrgSoGlLrcShEEBY");
        return Keys.hmacShaKeyFor(key);
        }

        public  boolean isTokenValid(String token, UserDetails userDetails ){
        final  String  username = extractUserName(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }
}