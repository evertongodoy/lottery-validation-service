package com.lottery.validation.infrastructure.adapters.input.rest.mappers;

import com.lottery.validation.application.dto.ConsecutiveNumbersDTO;
import com.lottery.validation.infrastructure.adapters.input.rest.responses.VerifyConsecutiveAverageResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class VerifyConsecutivesNumbersRestMapper {
    
    public VerifyConsecutiveAverageResponse toResponse(ConsecutiveNumbersDTO dto) {
        log.info("[toResponse] Convertendo ConsecutiveNumbersDTO para VerifyConsecutiveAverageResponse | lotteryType={}", 
                dto != null ? dto.getLotteryType() : null);
        
        if (dto == null) {
            log.warn("[toResponse] DTO é null, retornando null");
            return null;
        }
        
        List<VerifyConsecutiveAverageResponse.ConsecutiveNumberStatistic> statistics = dto.getConsecutiveAverage().stream()
                .map(stat -> {
                    // Mapear a lista de draws
                    List<VerifyConsecutiveAverageResponse.DrawPresence> drawsResponse = stat.getDraws() != null ? 
                            stat.getDraws().stream()
                                    .map(draw -> VerifyConsecutiveAverageResponse.DrawPresence.builder()
                                            .lotteryNumber(draw.getLotteryNumber())
                                            .present(draw.getPresent())
                                            .build())
                                    .collect(Collectors.toList()) 
                            : new ArrayList<>();
                    
                    return VerifyConsecutiveAverageResponse.ConsecutiveNumberStatistic.builder()
                            .number(stat.getNumber())
                            .consecutivePresentAverage(stat.getConsecutivePresentAverage())
                            .maxConsecutivePresent(stat.getMaxConsecutivePresent())
                            .minConsecutivePresent(stat.getMinConsecutivePresent())
                            .presentIntervals(stat.getPresentIntervals())
                            .consecutiveAbsentAverage(stat.getConsecutiveAbsentAverage())
                            .maxConsecutiveAbsent(stat.getMaxConsecutiveAbsent())
                            .minConsecutiveAbsent(stat.getMinConsecutiveAbsent())
                            .absentIntervals(stat.getAbsentIntervals())
                            .draws(drawsResponse)
                            .build();
                })
                .collect(Collectors.toList());
        
        log.info("[toResponse] Resposta gerada com {} estatísticas", statistics.size());
        
        return VerifyConsecutiveAverageResponse.builder()
                .lotteryType(dto.getLotteryType())
                .consecutiveAverage(statistics)
                .build();
    }
}
