package com.sumit.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;

@Service
public class JwtUtil {

    @Value("${jwt.token.256.bit.secret.key}")
    private String SECRET_KEY = null;

    @Value("${jwt.token.expiry.duration}")
    private Long TOKEN_EXPIRY_DURATION = null;

    public String generateToken(UserDetails userDetails, Map<String, Object> extraClaims){
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_EXPIRY_DURATION))  // 30 minutes expiry time
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // reading Token's Payload
    // extracting all Claims means reading all attributes from the Token Payload
    private Claims extractAllClaims(String token){
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_KEY));
    }



    public String extractUsername(String token){
        Claims claims = extractAllClaims(token);
        return claims.getSubject();
    }

    private Date extractTokenExpiry(String token) {
        Claims claims = extractAllClaims(token);
        return claims.getExpiration();
    }

     public boolean isTokenValid(String token, UserDetails userDetails){
        final String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractTokenExpiry(token).before(new Date());
    }

}