package com.mhl.mycompanybackend.configs.jwt;

import com.mhl.mycompanybackend.userdetails.UserDetailsImpl;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtils {

    @Value("${app.jwtSecret}")
    private String jwtSecret;

    public String generateJwtToken(Authentication authentication) {

        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("id", userPrincipal.getId());
        extraClaims.put("username", userPrincipal.getUsername());
        extraClaims.put("email", userPrincipal.getEmail());
        extraClaims.put("firstname", userPrincipal.getUserData().getFirstname());
        extraClaims.put("lastname", userPrincipal.getUserData().getLastname());
        extraClaims.put("middlename", userPrincipal.getUserData().getMiddlename());
        extraClaims.put("phone_number", userPrincipal.getUserData().getPhone_number());
        extraClaims.put("roles", userPrincipal.getRoles());
        return Jwts.builder()
                .setSubject((userPrincipal.getUsername())).setIssuedAt(new Date())
                .addClaims(extraClaims)
                .signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
    }

    public boolean validateJwtToken(String jwt) {
        try {
            Jwts.parserBuilder().setSigningKey(jwtSecret).build().parseClaimsJws(jwt);
            return true;
        } catch (MalformedJwtException | IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }

        return false;
    }

    public String getUserNameFromJwtToken(String jwt) {
        return Jwts.parserBuilder().setSigningKey(jwtSecret).build().parseClaimsJws(jwt).getBody().getSubject();
    }

}

