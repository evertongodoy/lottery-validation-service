package com.lottery.validation.application.dto;

import java.util.List;

import com.lottery.validation.domain.enums.LotteryType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FindTopFrequencyDTO {
    
    private LotteryType lotteryType;
    private Integer totalDraws;
    private List<NumbersRankDTO> numbersRank;

}