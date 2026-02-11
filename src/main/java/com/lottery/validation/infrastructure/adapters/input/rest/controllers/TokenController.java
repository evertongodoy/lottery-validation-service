package com.lottery.validation.infrastructure.adapters.input.rest.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lottery.validation.application.ports.input.TokenInputPort;
import com.lottery.validation.infrastructure.adapters.input.rest.mappers.TokenRestMapper;
import com.lottery.validation.infrastructure.adapters.input.rest.requests.TokenCreateRequest;
import com.lottery.validation.infrastructure.adapters.input.rest.responses.TokenResponse;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/token")
@Hidden
public class TokenController {

    private final TokenInputPort tokenInputPort;
    private final TokenRestMapper tokenRestMapper;

    public TokenController(TokenInputPort tokenInputPort, TokenRestMapper tokenRestMapper) {
        this.tokenInputPort = tokenInputPort;
        this.tokenRestMapper = tokenRestMapper;
    }

    @PostMapping("/create")
    public ResponseEntity<TokenResponse> createToken(@Valid @RequestBody TokenCreateRequest request) {
        log.info("[createToken] Início - Request Body: {}", request);
        var createTokenDTO = tokenRestMapper.toDTO(request);
        var tokenDTO = tokenInputPort.createToken(createTokenDTO);
        var response = tokenRestMapper.toResponse(tokenDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
