package com.lottery.validation.application.dto;

import com.lottery.validation.domain.enums.LotteryType;

public class SaveLotteryDTO {
    private LotteryType lotteryType;
    private Integer waitTimeSeconds;

    public SaveLotteryDTO() {
    }

    public SaveLotteryDTO(LotteryType lotteryType, Integer waitTimeSeconds) {
        this.lotteryType = lotteryType;
        this.waitTimeSeconds = waitTimeSeconds;
    }

    public LotteryType getLotteryType() {
        return lotteryType;
    }

    public void setLotteryType(LotteryType lotteryType) {
        this.lotteryType = lotteryType;
    }

    public Integer getWaitTimeSeconds() {
        return waitTimeSeconds;
    }

    public void setWaitTimeSeconds(Integer waitTimeSeconds) {
        this.waitTimeSeconds = waitTimeSeconds;
    }
}
