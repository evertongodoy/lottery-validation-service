package com.lottery.validation.infrastructure.adapters.input.rest.controllers;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lottery.validation.application.dto.FindLotteryDTO;
import com.lottery.validation.application.ports.input.FindLotteryInputPort;
import com.lottery.validation.application.ports.input.SaveLotteryInputPort;
import com.lottery.validation.domain.entities.Lottery;
import com.lottery.validation.domain.enums.LotteryType;
import com.lottery.validation.infrastructure.adapters.input.rest.mappers.SaveLotteryRestMapper;
import com.lottery.validation.infrastructure.adapters.input.rest.requests.RegisterLotteryRequest;
import com.lottery.validation.infrastructure.adapters.input.rest.responses.FindLotteryResponse;
import com.lottery.validation.infrastructure.adapters.input.rest.responses.SaveLotteryResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/lottery")
@Tag(name = "Lottery", description = "Lottery management endpoints")
public class LotteryController {

    private final SaveLotteryInputPort saveLotteryInputPort;
    private final FindLotteryInputPort findLotteryInputPort;
    private final SaveLotteryRestMapper saveLotteryRestMapper;

    public LotteryController(SaveLotteryInputPort saveLotteryInputPort, 
                           FindLotteryInputPort findLotteryInputPort,
                           SaveLotteryRestMapper saveLotteryRestMapper) {
        this.saveLotteryInputPort = saveLotteryInputPort;
        this.findLotteryInputPort = findLotteryInputPort;
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

    @GetMapping("/find-db-lottery/{lotteryType}")
    @Operation(summary = "Find lottery draws", description = "Retrieves lottery draws from database with pagination")
    public ResponseEntity<FindLotteryResponse> findLotteries(
            @PathVariable LotteryType lotteryType,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            @RequestParam(value = "orderBy", defaultValue = "lotteryNumber") String orderBy,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction) {
        
        FindLotteryDTO findLotteryDTO = new FindLotteryDTO(lotteryType, page, size, orderBy, direction);
        Page<Lottery> lotteryPage = findLotteryInputPort.findLottery(findLotteryDTO);
        
        FindLotteryResponse response = new FindLotteryResponse(
            lotteryPage.getContent().stream()
                .map(FindLotteryResponse.LotteryData::new)
                .collect(Collectors.toList()),
            lotteryPage.getNumber(),
            lotteryPage.getSize(),
            lotteryPage.getTotalElements(),
            lotteryPage.getTotalPages(),
            lotteryPage.isLast()
        );
        
        return ResponseEntity.ok(response);
    }
}
