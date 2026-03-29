package com.lottery.validation.application.ports.output;

import com.lottery.validation.domain.entities.Lottery;
import com.lottery.validation.domain.enums.LotteryType;

import java.util.List;

public interface VerifyConsecutivesNumbersOutputPort {
    
    List<Lottery> findLotteries(LotteryType lotteryType);
    
}
