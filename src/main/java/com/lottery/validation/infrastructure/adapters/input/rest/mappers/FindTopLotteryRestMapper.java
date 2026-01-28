package com.lottery.validation.infrastructure.adapters.input.rest.mappers;

import com.lottery.validation.application.dto.FindTopFrequencyDTO;
import com.lottery.validation.infrastructure.adapters.input.rest.responses.FindTopLotteryResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Slf4j
@Component
public class FindTopLotteryRestMapper {
    
    public FindTopLotteryResponse toResponse(FindTopFrequencyDTO dto) {
        log.info("[toResponse] | dto={}", dto);
        if (dto == null) {
            return null;
        }
        
        var numbersRankResponse = dto.getNumbersRank().stream()
                .map(rank -> new FindTopLotteryResponse.NumbersRank(
                        rank.position(),
                        rank.value(),
                        rank.repetitions()
                ))
                .collect(Collectors.toList());
        
        return new FindTopLotteryResponse(
                dto.getLotteryType(),
                dto.getTotalDraws(),
                numbersRankResponse
        );
    }
    
}
