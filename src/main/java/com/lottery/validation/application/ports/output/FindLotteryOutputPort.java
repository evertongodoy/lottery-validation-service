package com.lottery.validation.application.ports.output;

import com.lottery.validation.domain.entities.Lottery;
import com.lottery.validation.domain.enums.LotteryType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FindLotteryOutputPort {
    Page<Lottery> findByLotteryType(LotteryType lotteryType, Pageable pageable);
}
