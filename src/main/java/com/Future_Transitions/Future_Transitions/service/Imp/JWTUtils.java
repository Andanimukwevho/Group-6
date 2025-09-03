//package com.Future_Transitions.Future_Transitions.service.Imp;
//
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.JwtException;
//import io.jsonwebtoken.Jwts;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Component;
//import org.springframework.web.bind.annotation.CrossOrigin;
//
//import javax.crypto.SecretKey;
//import javax.crypto.spec.SecretKeySpec;
//import java.nio.charset.StandardCharsets;
//import java.util.Base64;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.function.Function;
//
//@Component
//public class JWTUtils {
//
//    private final SecretKey key;
//    private static final long EXPIRATION_TIME = 86400000; // 24hours
//
//    public JWTUtils(){
//        String secretString = "843567893696976453275974432697R634976R738467TR678T34865R6834R8763T478378637664538745673865783678548735687R3";
//        byte[] keyBytes = Base64.getDecoder().decode(secretString.getBytes(StandardCharsets.UTF_8));
//        this.key = new SecretKeySpec(keyBytes, "HmacSHA256");
//    }
//
//    // this is a method that generate a token it used jwts form dependecy to build the token method
//    public String generateToken(UserDetails userDetails){
//        return Jwts.builder()
//                .subject(userDetails.getUsername())
//                .issuedAt(new Date(System.currentTimeMillis()))
//                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
//                .signWith(key)
//                .compact();
//    }
//    //in case if the original token expire u get another refreshed one
//    public String generateRefreshToken(Map<String , Object> claims , UserDetails userDetails){
//        return Jwts.builder()
//                .claims(claims)
//                .subject(userDetails.getUsername())
//                .issuedAt(new Date(System.currentTimeMillis()))
//                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
//                .signWith(key)
//                .compact();
//    }
//
//    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
//        return claimsResolver.apply(extractAllClaims(token));
//    }
//
//    private Claims extractAllClaims(String token) {
//        try {
//            return Jwts.parser()
//                    .verifyWith(key)
//                    .build()
//                    .parseSignedClaims(token)
//                    .getPayload();
//        } catch (JwtException e) {
//            // Log or rethrow custom exception as needed
//            throw new RuntimeException("Invalid or expired JWT token", e);
//        }
//    }
//
//    public  String extractUsername(String token){
//        return  extractClaim(token, Claims::getSubject);
//    }
//    public  boolean isTokenValid(String token, UserDetails userDetails){
//        final String username = extractUsername(token);
//        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
//    }
//
//    public  boolean isTokenExpired(String token){
//        return extractClaim(token, Claims::getExpiration).before(new Date());
//    }
//
//
//
//
//
//}
