package com.project.apivenda.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtUtils {

    @Value("${app.security.jwt.secret}")
    private String jwtSecret;

    @Value("${app.security.jwt.access-token}")
    private int accessToken;

    @Value("${app.security.jwt.refresh-token}")
    private int refreshToken;

    private SecretKey key;

    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    public String generateToken(Authentication authentication) {
        UserDetails user = (UserDetails) authentication.getPrincipal();
        Map<String,Object> claims = new HashMap<>();

        assert user != null;

        claims.put("roles", user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList());


        return Jwts.builder().claims(claims)
                .subject(user.getUsername())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + accessToken))
                .signWith(key)
                .compact();
    }

    public String generateToken(String username) {
        return buildToken(new HashMap<>(), username, refreshToken);
    }

    public String generateRefreshToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("type", "REFRESH");
        return buildToken(claims, username, refreshToken);
    }

    private String buildToken(Map<String, Object> extraClaims, String username, int expiration) {
        return Jwts.builder()
                .claims(extraClaims)
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims extractAllClaimsFromToken(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String extractUsername(String token) {
        return extractAllClaimsFromToken(token).getSubject();
    }

    public boolean isTokenValid(String token) {
        try {
            extractAllClaimsFromToken(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (MalformedJwtException e) {
            log.error("❌ Token JWT malformado: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("⏰ Token JWT expirado: {}", e.getMessage());
            // Não relançar a exceção, apenas retornar false
        } catch (UnsupportedJwtException e) {
            log.error("❌ Token JWT não suportado: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("❌ Token JWT vazio: {}", e.getMessage());
        } catch (Exception e) {
            log.error("❌ Erro ao validar token: {}", e.getMessage());
        }
        return false;
    }

    public long getExpirationTime() {
        return refreshToken;
    }

    public long getRefreshExpirationTime() {
        return refreshToken;
    }

    public boolean isRefreshToken(String token) {
        try {
            Claims claims = getAllClaimsFromToken(token);
            return "REFRESH".equals(claims.get("type"));
        } catch (Exception e) {
            return false;
        }
    }
}
