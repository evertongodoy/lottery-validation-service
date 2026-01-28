package com.lottery.validation.infrastructure.adapters.input.rest.mappers;

import com.lottery.validation.application.dto.WinnersDTO;
import com.lottery.validation.infrastructure.adapters.input.rest.responses.VerifyUserDrawWinnerResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class VerifyUserDrawWinnerRestMapper {
    
    public VerifyUserDrawWinnerResponse toResponse(WinnersDTO winnersDTO) {
        log.info("[toResponse] | winnersDTO={}", winnersDTO);
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
