package com.lottery.validation.application.ports.output;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.lottery.validation.domain.entities.UserDraw;
import com.lottery.validation.domain.enums.LotteryType;

public interface FindMyDrawOutputPort {
    
    Page<UserDraw> findMyDrawByLotteryType(UUID uuidSubject, LotteryType lotteryType, Pageable pageable);

}
