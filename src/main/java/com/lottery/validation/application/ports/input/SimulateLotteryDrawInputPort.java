package com.lottery.validation.application.ports.input;

import java.util.List;

import com.lottery.validation.application.dto.SimulateLotteryDrawDTO;
import com.lottery.validation.domain.enums.LotteryType;

public interface SimulateLotteryDrawInputPort {
    
    SimulateLotteryDrawDTO simulateLotteryDraw(LotteryType lotteryType, List<Integer> numbers);
    
}
