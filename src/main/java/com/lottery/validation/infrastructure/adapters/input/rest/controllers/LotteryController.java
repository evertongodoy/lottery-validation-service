package com.lottery.validation.infrastructure.adapters.input.rest.controllers;

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
import com.lottery.validation.application.ports.input.FindTopLotteryInputPort;
import com.lottery.validation.application.ports.input.SaveLotteryInputPort;
import com.lottery.validation.domain.enums.LotteryType;
import com.lottery.validation.infrastructure.adapters.input.rest.mappers.FindLotteryRestMapper;
import com.lottery.validation.infrastructure.adapters.input.rest.mappers.FindTopLotteryRestMapper;
import com.lottery.validation.infrastructure.adapters.input.rest.mappers.SaveLotteryRestMapper;
import com.lottery.validation.infrastructure.adapters.input.rest.requests.RegisterLotteryRequest;
import com.lottery.validation.infrastructure.adapters.input.rest.responses.FindLotteryResponse;
import com.lottery.validation.infrastructure.adapters.input.rest.responses.FindTopLotteryResponse;
import com.lottery.validation.infrastructure.adapters.input.rest.responses.SaveLotteryResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/lottery")
@Tag(name = "Lottery", description = "Lottery management endpoints")
public class LotteryController {

    private final SaveLotteryInputPort saveLotteryInputPort;
    private final FindLotteryInputPort findLotteryInputPort;
    private final FindTopLotteryInputPort findTopLotteryInputPort;
    private final SaveLotteryRestMapper saveLotteryRestMapper;
    private final FindLotteryRestMapper findLotteryRestMapper;
    private final FindTopLotteryRestMapper findTopLotteryRestMapper;

    public LotteryController(SaveLotteryInputPort saveLotteryInputPort,
                           FindLotteryInputPort findLotteryInputPort,
                           SaveLotteryRestMapper saveLotteryRestMapper,
                           FindLotteryRestMapper findLotteryRestMapper,
                           FindTopLotteryInputPort findTopLotteryInputPort,
                           FindTopLotteryRestMapper findTopLotteryRestMapper) {
        this.saveLotteryInputPort = saveLotteryInputPort;
        this.findLotteryInputPort = findLotteryInputPort;
        this.saveLotteryRestMapper = saveLotteryRestMapper;
        this.findLotteryRestMapper = findLotteryRestMapper;
        this.findTopLotteryInputPort = findTopLotteryInputPort;
        this.findTopLotteryRestMapper = findTopLotteryRestMapper;
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
        var resultDTO = findLotteryInputPort.findLottery(findLotteryDTO);
        var response = findLotteryRestMapper.toResponse(resultDTO);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/find-top-numbers/lottery-type/{lotteryType}")
    @Operation(summary = "Find top frequent lottery numbers", description = "Retrieves the most frequently drawn lottery numbers for a given lottery type")
    public ResponseEntity<FindTopLotteryResponse> getMethodName(@PathVariable LotteryType lotteryType) {
        var findTopFrequencyDTO = findTopLotteryInputPort.findTopLottery(lotteryType);
        var response = findTopLotteryRestMapper.toResponse(findTopFrequencyDTO);;   
        return ResponseEntity.ok(response);
    }
    
}
