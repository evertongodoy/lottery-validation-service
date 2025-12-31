package com.lottery.validation.application.ports.output;

import java.util.List;

import com.lottery.validation.domain.entities.Lottery;
import com.lottery.validation.domain.enums.LotteryType;

public interface SimulateLotteryDrawOutputPort {
    
    List<Lottery> findLotteries(LotteryType lotteryType);
    
}
