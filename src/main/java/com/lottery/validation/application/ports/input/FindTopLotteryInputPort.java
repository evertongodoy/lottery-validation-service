package com.lottery.validation.application.ports.input;

import com.lottery.validation.domain.enums.LotteryType;

public interface FindTopLotteryInputPort {

    void findTopLottery(LotteryType lotteryType);
    
}
