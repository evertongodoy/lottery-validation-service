package com.lottery.validation.application.dto;

import com.lottery.validation.domain.enums.LotteryType;

public record FindLotteryDTO(
    LotteryType lotteryType,
    Integer page,
    Integer size,
    String orderBy,
    String direction
) {
}
