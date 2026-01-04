package com.lottery.validation.application.ports.input;

import com.lottery.validation.application.dto.WinnersDTO;
import com.lottery.validation.domain.enums.LotteryType;

public interface VerifyUserDrawInputPort {
    
    WinnersDTO verifyUserDraws(LotteryType lotteryType);
    
}