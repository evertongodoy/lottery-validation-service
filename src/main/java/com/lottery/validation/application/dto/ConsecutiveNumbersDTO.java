package com.lottery.validation.application.dto;

import com.lottery.validation.domain.enums.LotteryType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConsecutiveNumbersDTO {
    
    private LotteryType lotteryType;
    private List<ConsecutiveNumberStatistic> consecutiveAverage;
    
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ConsecutiveNumberStatistic {
        private Integer number;
        private Double consecutivePresentAverage;
        private Integer maxConsecutivePresent;
        private Integer minConsecutivePresent;
        private Integer presentIntervals;
        private Double consecutiveAbsentAverage;
        private Integer maxConsecutiveAbsent;
        private Integer minConsecutiveAbsent;
        private Integer absentIntervals;
        private List<DrawPresence> draws;
    }
    
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DrawPresence {
        private Integer lotteryNumber;
        private String present; // "P" para presente, "A" para ausente
    }
}
