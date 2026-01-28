package com.lottery.validation.infrastructure.adapters.input.rest.mappers;

import com.lottery.validation.application.dto.FindLotteryResultDTO;
import com.lottery.validation.application.dto.LotteryDTO;
import com.lottery.validation.infrastructure.adapters.input.rest.responses.FindLotteryResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Slf4j
@Component
public class FindLotteryRestMapper {

    public FindLotteryResponse toResponse(FindLotteryResultDTO resultDTO) {
        log.info("[toResponse] | resultDTO={}", resultDTO);
        if (resultDTO == null) {
            return null;
        }
        
        var responseContent = resultDTO.getContent().stream()
            .map(this::toLotteryDataResponse)
            .collect(Collectors.toList());
        
        return new FindLotteryResponse(
            responseContent,
            resultDTO.getPage(),
            resultDTO.getSize(),
            resultDTO.getTotalElements(),
            resultDTO.getTotalPages(),
            resultDTO.getLast()
        );
    }
    
    private FindLotteryResponse.LotteryData toLotteryDataResponse(LotteryDTO dto) {
        var response = new FindLotteryResponse.LotteryData();
        response.setId(dto.getId());
        response.setLotteryNumber(dto.getLotteryNumber());
        response.setNextLotteryNumber(dto.getNextLotteryNumber());
        response.setAddAt(dto.getAddAt());
        response.setDrawDate(dto.getDrawDate());
        response.setDrawnNumbers(dto.getDrawnNumbers());
        response.setSortedDrawNumbers(dto.getSortedDrawNumbers());
        response.setCity(dto.getCity());
        response.setLotteryType(dto.getLotteryType());
        return response;
    }
}
