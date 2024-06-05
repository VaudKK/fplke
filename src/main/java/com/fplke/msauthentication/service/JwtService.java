package com.fplke.msauthentication.service;

import com.fplke.msauthentication.models.User;
import io.jsonwebtoken.Claims;

import java.util.Map;
import java.util.function.Function;

public interface JwtService {

    String extractUserId(String token);
    <T> T extractClaims(String token, Function<Claims,T> claimsResolver);
    String generateToken(User user);
    String generateToken(Map<String,Object> claims,User user);
    boolean isTokenValid(String token,User user);
    long getExpiresIn();
}
