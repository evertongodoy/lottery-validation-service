package com.lottery.validation.application.usecases.user;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import javax.crypto.SecretKey;

import com.lottery.validation.application.dto.CreateTokenDTO;
import com.lottery.validation.application.dto.TokenDTO;
import com.lottery.validation.application.ports.input.TokenInputPort;
import com.lottery.validation.application.ports.output.UserOutputPort;
import com.lottery.validation.domain.exceptions.BusinessException;
import com.lottery.validation.infrastructure.config.JwtProperties;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TokenUseCase implements TokenInputPort {

    private final UserOutputPort userOutputPort;
    private final JwtProperties jwtProperties;

    public TokenUseCase(UserOutputPort userOutputPort, JwtProperties jwtProperties) {
        this.userOutputPort = userOutputPort;
        this.jwtProperties = jwtProperties;
    }

    @Override
    public TokenDTO createToken(CreateTokenDTO dto) {
        log.info("[createToken] | dto={}", dto);
        
        var user = userOutputPort.findBySubject(dto.getSubject())
                .orElseThrow(() -> new BusinessException("Subject ou password incorretos"));

        if (!user.getPassword().equals(dto.getPassword())) {
            throw new BusinessException("Subject ou password incorretos");
        }

        // Gerar token JWT
        var token = generateJwtToken(user.getSubject(), user.getRole().name());
        return TokenDTO.builder()
            .token(token)
        .build();
       
    }

    private String generateJwtToken(String subject, String role) {
        // Decodificar a chave secreta de base64 para String
        var decodedKey = java.util.Base64.getDecoder().decode(jwtProperties.getSecretKey());
        
        // Criar a chave secreta a partir da string decodificada
        var secretKey = Keys.hmacShaKeyFor(decodedKey);

        // Calcular tempo de expiração
        var now = Instant.now();
        var expiration = now.plus(jwtProperties.getExpirationMinutes(), ChronoUnit.MINUTES);

        // Criar o token JWT com HS256
        return Jwts.builder()
                .subject(subject)
                .claim("scope", role)
                .issuedAt(Date.from(now))
                .expiration(Date.from(expiration))
                .signWith(secretKey, Jwts.SIG.HS256)
                .compact();
    }
}
