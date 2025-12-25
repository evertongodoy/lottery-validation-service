package com.lottery.validation.infrastructure.adapters.input.rest.requests;

import com.lottery.validation.domain.enums.LotteryType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public class RegisterLotteryRequest {

    @NotNull(message = "Lottery type is required")
    @Schema(description = "Type of lottery", example = "LOTOFACIL")
    private LotteryType lotteryType;

    public RegisterLotteryRequest() {
    }

    public RegisterLotteryRequest(LotteryType lotteryType) {
        this.lotteryType = lotteryType;
    }

    public LotteryType getLotteryType() {
        return lotteryType;
    }

    public void setLotteryType(LotteryType lotteryType) {
        this.lotteryType = lotteryType;
    }
}
