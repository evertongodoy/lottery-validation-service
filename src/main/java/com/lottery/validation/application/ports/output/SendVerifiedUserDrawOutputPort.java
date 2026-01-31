package com.lottery.validation.application.ports.output;

import java.util.List;
import java.util.UUID;

import com.lottery.validation.domain.entities.Winners;
import com.lottery.validation.domain.enums.LotteryType;

public interface SendVerifiedUserDrawOutputPort {
    
    List<Winners> findWinnersNotSent(LotteryType lotteryType);

    Winners updateMessageSentStatus(UUID uuidDraw, Integer lotteryNumber, Boolean messageSent);
    
}
