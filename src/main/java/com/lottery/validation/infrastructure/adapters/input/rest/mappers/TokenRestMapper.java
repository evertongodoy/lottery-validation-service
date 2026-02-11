package com.lottery.validation.infrastructure.adapters.input.rest.mappers;

import lombok.extern.slf4j.Slf4j;

import java.util.Base64;

import org.springframework.stereotype.Component;

import com.lottery.validation.application.dto.CreateTokenDTO;
import com.lottery.validation.application.dto.TokenDTO;
import com.lottery.validation.infrastructure.adapters.input.rest.requests.TokenCreateRequest;
import com.lottery.validation.infrastructure.adapters.input.rest.responses.TokenResponse;

@Slf4j
@Component
public class TokenRestMapper {

    public CreateTokenDTO toDTO(TokenCreateRequest request) {
        log.info("[toDTO] | request={}", request);
        return CreateTokenDTO.builder()
            .subject(request.getSubject())
            .password(Base64.getEncoder().encodeToString(request.getPassword().getBytes()))
        .build();
    }

    public TokenResponse toResponse(TokenDTO dto) {
        log.info("[toResponse] | dto={}", dto);
        return TokenResponse.builder()
            .token(dto.getToken())
        .build();
    }
}
