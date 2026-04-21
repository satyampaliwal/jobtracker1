package com.satyam.jobtracke1.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
//import lombok.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
@Component
public class JwtUtil {
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    // Token banana
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // Token se username nikalna
    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // Token valid hai ya nahi check karna
    public boolean isTokenValid(String token, String username) {
        String extractedUsername = extractUsername(token);
        return extractedUsername.equals(username) && !isTokenExpired(token);
    }

    // Token expire hua ya nahi
    private boolean isTokenExpired(String token) {
        Date expiry = Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
        return expiry.before(new Date());
    }

    // Secret key ko proper format mein convert karna
    private Key getSignKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }
}
