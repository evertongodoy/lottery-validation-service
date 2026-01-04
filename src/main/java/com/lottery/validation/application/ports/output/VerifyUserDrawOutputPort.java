package com.lottery.validation.application.ports.output;

import java.util.List;
import java.util.Map;

import com.lottery.validation.domain.entities.UserDraw;
import com.lottery.validation.domain.entities.Winners;
import com.lottery.validation.domain.enums.LotteryType;

public interface VerifyUserDrawOutputPort {
    
    Map<String, Object> findWebLottery(LotteryType lotteryType);
    
    List<UserDraw> findActiveUserDrawsByLotteryType(LotteryType lotteryType);
    
    Winners saveWinners(Winners winners);
    
}
