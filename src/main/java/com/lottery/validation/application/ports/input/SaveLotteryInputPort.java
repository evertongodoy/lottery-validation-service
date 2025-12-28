package com.lottery.validation.application.ports.input;

import com.lottery.validation.application.dto.SaveLotteryDTO;
import com.lottery.validation.application.dto.SaveLotteryResultDTO;

public interface SaveLotteryInputPort {
    
    SaveLotteryResultDTO saveLottery(SaveLotteryDTO saveLotteryDTO);
    
}
