package com.lottery.validation.application.ports.input;

import com.lottery.validation.application.dto.ConsecutiveNumbersDTO;
import com.lottery.validation.domain.enums.LotteryType;

public interface VerifyConsecutivesNumbersInputPort {
    
    ConsecutiveNumbersDTO verifyConsecutives(LotteryType lotteryType);
    
}
