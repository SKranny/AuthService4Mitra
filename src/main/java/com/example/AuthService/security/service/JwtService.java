package com.example.AuthService.security.service;

import com.example.AuthService.constants.RoleType;
import com.example.AuthService.security.PersonDetails;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static io.jsonwebtoken.SignatureAlgorithm.HS512;

@Service
@Slf4j
public class JwtService {
    @Value("${spring.application.name}")
    private String appName;

    @Value("${jwt.secret-code}")
    private String secretKey;

    @Value("${jwt.life-time}")
    private Long lifeTime;

    public String generateJwtToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        PersonDetails personDetails = (PersonDetails) userDetails;

        claims.put("id", personDetails.getId());
        claims.put("username", personDetails.getUsername());
        claims.put("roles", personDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet()));

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(personDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + lifeTime))
                .signWith(HS512, secretKey)
                .compact();
    }

    public String generateDefaultToken() {
        Map<String, Object> claims = new HashMap<>();

        claims.put("id", System.currentTimeMillis());
        claims.put("username", "");
        claims.put("roles", Stream.of(RoleType.ROLE_USER.name())
                .collect(Collectors.toSet()));

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(appName)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 60000))
                .signWith(HS512, secretKey)
                .compact();
    }
}
