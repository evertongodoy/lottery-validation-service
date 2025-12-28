package com.lottery.validation.infrastructure.adapters.input.rest.mappers;

import com.lottery.validation.application.dto.FindLotteryResultDTO;
import com.lottery.validation.infrastructure.adapters.input.rest.responses.FindLotteryResponse;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class FindLotteryRestMapper {

    public FindLotteryResponse toResponse(FindLotteryResultDTO resultDTO) {
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
    
    private FindLotteryResponse.LotteryData toLotteryDataResponse(FindLotteryResultDTO.LotteryData data) {
        var response = new FindLotteryResponse.LotteryData();
        response.setId(data.getId());
        response.setLotteryNumber(data.getLotteryNumber());
        response.setNextLotteryNumber(data.getNextLotteryNumber());
        response.setAddAt(data.getAddAt());
        response.setDrawDate(data.getDrawDate());
        response.setDrawnNumbers(data.getDrawnNumbers());
        response.setSortedDrawNumbers(data.getSortedDrawNumbers());
        response.setCity(data.getCity());
        response.setLotteryType(data.getLotteryType());
        return response;
    }
}
