package com.lottery.validation.application.ports.output;

import com.lottery.validation.domain.enums.LotteryType;

public interface FindTopLotteryOutputPort {

    void findLotteries(LotteryType lotteryType);
    
}
