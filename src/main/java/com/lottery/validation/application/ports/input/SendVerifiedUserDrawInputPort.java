package com.lottery.validation.application.ports.input;

import com.lottery.validation.domain.enums.LotteryType;

public interface SendVerifiedUserDrawInputPort {
    
    void sendVerifiedWinnerDraw(LotteryType lotteryType);
    
}
