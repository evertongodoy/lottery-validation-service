package com.lottery.validation.infrastructure.adapters.input.rest.mappers;

import com.lottery.validation.application.dto.WinnersDTO;
import com.lottery.validation.infrastructure.adapters.input.rest.responses.VerifyUserDrawWinnerResponse;
import org.springframework.stereotype.Component;

@Component
public class VerifyUserDrawWinnerRestMapper {
    
    public VerifyUserDrawWinnerResponse toResponse(WinnersDTO winnersDTO) {
        if (winnersDTO == null) {
            return null;
        }
        
        return VerifyUserDrawWinnerResponse.builder()
                .totalWinners(winnersDTO.getTotalWinners())
                .lotteryType(winnersDTO.getLotteryType())
                .lotteryNumber(winnersDTO.getLotteryNumber())
                .build();
    }
}
