package com.lauren.lucided.security;

import io.jsonwebtoken.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    private final String jwtSecret = "lucided5secret4key44885544844bG9naWNhbGx5U2FmZVNlY3JldEtleTElucided5secret4key44885544844bG9naWNhbGx5U2FmZVNlY3JldEtleTE"; // move to env in production
    private final long jwtExpirationMs = 86400000; // 1 day

    // ✅ Option 1: Generate token from Authentication object
    public String generateToken(Authentication auth) {
        String email = auth.getName();
        String role = auth.getAuthorities().iterator().next().getAuthority().replace("ROLE_", "");
        return generateToken(email, role);
    }

    // ✅ Option 2: Generate token from email + role directly
    public String generateToken(String email, String role) {
        return Jwts.builder()
                .setSubject(email)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    // ✅ Validate token
    public boolean isTokenValid(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    // ✅ Extract username (email)
    public String extractUsername(String token) {
        return getClaims(token).getSubject();
    }

    // ✅ Extract role
    public String extractRole(String token) {
        return getClaims(token).get("role", String.class);
    }

    // ✅ Internal helper
    private Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();
    }
}