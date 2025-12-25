package com.lottery.validation.infrastructure.adapters.input.rest.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lottery.validation.application.ports.input.SaveLotteryInputPort;
import com.lottery.validation.infrastructure.adapters.input.rest.mappers.SaveLotteryRestMapper;
import com.lottery.validation.infrastructure.adapters.input.rest.requests.RegisterLotteryRequest;
import com.lottery.validation.infrastructure.adapters.input.rest.responses.SaveLotteryResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/lottery")
@Tag(name = "Lottery", description = "Lottery management endpoints")
public class LotteryController {

    private final SaveLotteryInputPort saveLotteryInputPort;
    private final SaveLotteryRestMapper saveLotteryRestMapper;

    public LotteryController(SaveLotteryInputPort saveLotteryInputPort, SaveLotteryRestMapper saveLotteryRestMapper) {
        this.saveLotteryInputPort = saveLotteryInputPort;
        this.saveLotteryRestMapper = saveLotteryRestMapper;
    }

    @PostMapping("/register")
    @Operation(summary = "Register lottery draws", description = "Fetches and registers lottery draws from external API")
    public ResponseEntity<SaveLotteryResponse> registerLottery(@Valid @RequestBody RegisterLotteryRequest request) {
        var saveLotteryDTO = saveLotteryRestMapper.toDTO(request);
        var result = saveLotteryInputPort.saveLottery(saveLotteryDTO);
        var response = saveLotteryRestMapper.toResponse(result);
        return ResponseEntity.status(response.getDrawCount() > 0 ? HttpStatus.CREATED : HttpStatus.OK).body(response);
    }
}
