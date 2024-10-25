package com.estudos.br.pdv.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.Date;

@Service
public class JWTService {

    @Value("${security.jwt.expiration}")
    private int expiration;

    @Value("${security.jwt.key}")
    private String key;

    public String generateToken(String username) {
        Calendar currentDate = Calendar.getInstance();
        currentDate.add(Calendar.MINUTE, expiration);
        Date expirationDate = currentDate.getTime();

        SecretKey secretKey = getSecretKey();

        return Jwts.builder()
                .setSubject(username)
                .setExpiration(expirationDate)
                .signWith(secretKey)
                .compact();
    }

    public String getUsername(String token) {
        return getClaims(token).getSubject();
    }

    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8));
    }

    private Claims getClaims(String token) {
        SecretKey secretKey = getSecretKey();

        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

}
