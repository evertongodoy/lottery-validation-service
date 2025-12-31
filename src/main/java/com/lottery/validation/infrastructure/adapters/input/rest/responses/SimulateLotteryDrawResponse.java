package com.lottery.validation.infrastructure.adapters.input.rest.responses;

import java.time.LocalDate;
import java.util.List;

import com.lottery.validation.domain.enums.LotteryType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SimulateLotteryDrawResponse {
    
    private LotteryType lotteryType;
    private List<LotteryMatch> matches;
    
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class LotteryMatch {
        private Integer lotteryNumber;
        private LocalDate drawDate;
        private Integer totalMatches;
        private List<Integer> drawNumbers;
    }
    
}
