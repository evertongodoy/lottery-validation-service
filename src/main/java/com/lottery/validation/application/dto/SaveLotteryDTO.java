package com.lottery.validation.application.dto;

import com.lottery.validation.domain.enums.LotteryType;

public class SaveLotteryDTO {
    private LotteryType lotteryType;

    public SaveLotteryDTO() {
    }

    public SaveLotteryDTO(LotteryType lotteryType) {
        this.lotteryType = lotteryType;
    }

    public LotteryType getLotteryType() {
        return lotteryType;
    }

    public void setLotteryType(LotteryType lotteryType) {
        this.lotteryType = lotteryType;
    }
}
