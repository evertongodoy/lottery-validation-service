package com.lottery.validation.application.ports.output;

import java.util.List;
import java.util.UUID;

import com.lottery.validation.domain.entities.UserDraw;
import com.lottery.validation.domain.entities.Winners;
import com.lottery.validation.domain.enums.LotteryType;

public interface SendVerifiedUserDrawOutputPort {
    
    List<Winners> findWinners(LotteryType lotteryType);
    
    UserDraw findUserDrawByUuid(UUID uuidDraw);
    
}
