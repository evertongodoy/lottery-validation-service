package com.lottery.validation.infrastructure.adapters.input.rest.mappers;

import com.lottery.validation.application.dto.FindTopFrequencyDTO;
import com.lottery.validation.infrastructure.adapters.input.rest.responses.FindTopLotteryResponse;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class FindTopLotteryRestMapper {
    
    public FindTopLotteryResponse toResponse(FindTopFrequencyDTO dto) {
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
