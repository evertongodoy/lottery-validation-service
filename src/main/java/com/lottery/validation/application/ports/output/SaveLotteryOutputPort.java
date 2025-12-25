package com.lottery.validation.application.ports.output;

import com.lottery.validation.domain.entities.Lottery;
import com.lottery.validation.domain.enums.LotteryType;

import java.util.Optional;

public interface SaveLotteryOutputPort {
    Lottery save(Lottery lottery);
    
    Optional<Lottery> findTopByLotteryTypeOrderByLotteryNumberDesc(LotteryType lotteryType);
}
