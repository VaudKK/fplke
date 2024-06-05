package com.fplke.msauthentication.service.impl;

import com.fplke.msauthentication.models.User;
import com.fplke.msauthentication.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtServiceImpl implements JwtService {

    @Value("${jwt.secret-key}")
    private String secret;

    @Value("${jwt.expires-in}")
    private Long expiresIn;

    @Override
    public String extractUserId(String token) {
        return extractClaims(token, Claims::getSubject);
    }

    @Override
    public <T> T extractClaims(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    @Override
    public String generateToken(User user) {
        return generateToken(new HashMap<>(),user);
    }

    @Override
    public String generateToken(Map<String, Object> claims, User user) {
        return buildToken(claims,user,expiresIn);
    }

    @Override
    public boolean isTokenValid(String token,User user) {
        var userId = extractUserId(token);
        return (userId.equals(user.getUserId())) && !isTokenExpired(token);
    }

    @Override
    public long getExpiresIn() {
        return expiresIn;
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaims(token, Claims::getExpiration);
    }

    private String buildToken(Map<String, Object> claims, User user,long expiresIn) {
        return Jwts
                .builder()
                .setClaims(claims)
                .setSubject(user.getUserId())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiresIn))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getKey(){
        var keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private Claims extractClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
