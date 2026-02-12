package com.lottery.validation.infrastructure.config.security;

import java.util.Base64;

import org.springframework.stereotype.Service;

import com.lottery.validation.domain.enums.UserRole;
import com.lottery.validation.domain.exceptions.UnauthorizedException;
import com.lottery.validation.infrastructure.config.JwtProperties;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class JwtService {

    private final JwtProperties jwtProperties;

    public JwtService(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    /**
     * Valida o token JWT e retorna os claims
     */
    public Claims validateToken(String token) {
        try {
            // Decodificar a chave secreta de base64
            byte[] decodedKey = Base64.getDecoder().decode(jwtProperties.getSecretKey());
            var secretKey = Keys.hmacShaKeyFor(decodedKey);

            // Parsear e validar o token
            return Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
                    
        } catch (ExpiredJwtException e) {
            log.error("[validateToken] Token expirado: {}", e.getMessage());
            throw new UnauthorizedException("Token expirado");
        } catch (MalformedJwtException e) {
            log.error("[validateToken] Token mal formatado: {}", e.getMessage());
            throw new UnauthorizedException("Token inválido");
        } catch (SignatureException e) {
            log.error("[validateToken] Assinatura do token inválida: {}", e.getMessage());
            throw new UnauthorizedException("Token inválido");
        } catch (Exception e) {
            log.error("[validateToken] Erro ao validar token: {}", e.getMessage());
            throw new UnauthorizedException("Erro ao validar token");
        }
    }

    /**
     * Extrai o subject (e-mail) do token
     */
    public String getSubjectFromToken(String token) {
        Claims claims = validateToken(token);
        return claims.getSubject();
    }

    /**
     * Extrai o scope (role) do token
     */
    public UserRole getRoleFromToken(String token) {
        Claims claims = validateToken(token);
        String scope = claims.get("scope", String.class);
        
        if (scope == null) {
            throw new UnauthorizedException("Token sem informação de role");
        }
        
        try {
            return UserRole.valueOf(scope);
        } catch (IllegalArgumentException e) {
            log.error("[getRoleFromToken] Role inválida no token: {}", scope);
            throw new UnauthorizedException("Role inválida no token");
        }
    }

    /**
     * Verifica se o token tem uma das roles permitidas
     */
    public boolean hasRole(String token, UserRole... allowedRoles) {
        UserRole tokenRole = getRoleFromToken(token);
        
        for (UserRole allowedRole : allowedRoles) {
            if (tokenRole == allowedRole) {
                return true;
            }
        }
        
        return false;
    }

    /**
     * Extrai o token do header Authorization
     * Formato esperado: "Bearer <token>"
     */
    public String extractTokenFromHeader(String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new UnauthorizedException("Header Authorization não encontrado ou inválido");
        }
        
        return authorizationHeader.substring(7); // Remove "Bearer "
    }
}
