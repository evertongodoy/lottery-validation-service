package com.lottery.validation.application.dto;

import com.lottery.validation.domain.enums.LotteryType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WinnersDTO {
    private Integer totalWinners;
    private LotteryType lotteryType;
    private Integer lotteryNumber;
}
