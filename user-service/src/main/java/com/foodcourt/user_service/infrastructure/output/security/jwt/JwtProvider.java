package com.foodcourt.user_service.infrastructure.output.security.jwt;

import com.foodcourt.user_service.domain.model.User;
import com.foodcourt.user_service.domain.spi.IUserPersistencePort;
import com.foodcourt.user_service.infrastructure.utils.InfrastructureConstants;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtProvider {
    private String secret;
    private long expiration;
    private final IUserPersistencePort userPersistencePort;

    public JwtProvider(@Value("${jwt.secret}") String secret,
                       @Value("${jwt.expiration}") long expiration,
                       IUserPersistencePort userPersistencePort) {
        this.secret = secret;
        this.expiration = expiration;
        this.userPersistencePort = userPersistencePort;
    }

    private SecretKey getSigningKey() {
        byte[] keyBytes = secret.getBytes();
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(Authentication authentication) {
        String username = authentication.getName();
        List<String> roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        User user = userPersistencePort.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(InfrastructureConstants.USER_NOT_FOUND_MESSAGE + username));

        JwtBuilder tokenBuilder = Jwts.builder()
                .setSubject(username)
                .claim(InfrastructureConstants.CLAIM_ROLES, roles)
                .claim(InfrastructureConstants.CLAIM_ID, user.getId())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration));

        if (roles.contains(InfrastructureConstants.ROLE_OWNER)) {
            Long restaurantId = user.getRestaurantId();
            if (restaurantId != null) {
                tokenBuilder.claim(InfrastructureConstants.CLAIM_RESTAURANT_ID, restaurantId);
            }
        }

        return tokenBuilder.signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public Authentication getAuthentication(String token) {
        var claims = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

        String username = claims.getSubject();
        List<String> roles = claims.get(InfrastructureConstants.CLAIM_ROLES, List.class);
        List<GrantedAuthority> authorities = roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        return new UsernamePasswordAuthenticationToken(username, null, authorities);
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
