package com.lottery.validation.infrastructure.adapters.input.rest.responses;

import java.util.List;
import com.lottery.validation.domain.enums.LotteryType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FindTopLotteryResponse {
    
    private LotteryType lotteryType;
    private Integer totalDraws;
    private List<NumbersRank> numbersRank;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class NumbersRank {
        private Integer position;
        private Integer value;
        private Long repetitions;
    }

}
