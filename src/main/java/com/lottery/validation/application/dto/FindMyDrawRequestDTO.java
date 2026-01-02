package com.lottery.validation.application.dto;

import com.lottery.validation.domain.enums.LotteryType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FindMyDrawRequestDTO {
    private LotteryType lotteryType;
    private Integer page;
    private Integer size;
    private String orderBy;
    private String direction;
    private String subject;
}
