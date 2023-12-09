package com.sumit.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtUtil {

    private static final String SECRET_KEY = "e419f64e92b161088e035d06018a17ccd85ae0f0127bfdd805e48f4f784d9525";

    public String generateToken(UserDetails userDetails, Map<String, Object> extraClaims){
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 30*60*1000))  // 30mins expiry time
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // extracting all Claims means reading all attributes from the Token Payload
    private Claims readTokenPayload(String token){
        Claims claims =  Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims;
    }

    private Key getSignInKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_KEY));
    }




    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = readTokenPayload(token);
        return claimsResolver.apply(claims);
    }

    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);
    }

    private Date extractTokenExpiry(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

     public boolean isTokenValid(String token, UserDetails userDetails){
        final String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractTokenExpiry(token).before(new Date());
    }

}