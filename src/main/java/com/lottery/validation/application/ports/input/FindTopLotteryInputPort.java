package com.lottery.validation.application.ports.input;

import java.util.List;

import com.lottery.validation.application.dto.FindTopFrequencyDTO;
import com.lottery.validation.domain.enums.LotteryType;

public interface FindTopLotteryInputPort {

    FindTopFrequencyDTO findTopLottery(LotteryType lotteryType);
    
}
