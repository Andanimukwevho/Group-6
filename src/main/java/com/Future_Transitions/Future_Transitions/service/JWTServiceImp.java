package com.Future_Transitions.Future_Transitions.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;

@Component
public class JWTServiceImp implements JWTService {


    @Value("${jwt.secret}")
    private String secret;

    private Key key;

    @PostConstruct
    public void init() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);

    }
    private static final long EXPIRATION_TIME = 86400000; // 24 hours

    public String generateToken(UserDetails userDetails){

        HashMap<String, Object> claims = new HashMap<>();
        var roles = userDetails.getAuthorities()
                .stream()
                .map(auth -> auth.getAuthority())
                .toList();

        claims.put("roles", roles);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt((new Date(System.currentTimeMillis())))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
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
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractUserName(String token){
        return extractClaim(token, Claims::getSubject);
        }
        private <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = extractAllClaim(token);
        return claimsResolver.apply(claims);
    }

    public List<String> extractRoles(String token) {
        Claims claims = extractAllClaim(token);
        return claims.get("roles", List.class);
    }

    private Claims extractAllClaim(String token) {
        return Jwts.parser().setSigningKey(getSignKey()).build().parseClaimsJws(token).getBody();
    }

    private Key getSignKey() {
        return key;
        }

        public  boolean isTokenValid(String token, UserDetails userDetails ){
        final  String  username = extractUserName(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }
}