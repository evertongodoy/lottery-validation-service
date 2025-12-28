package com.lottery.validation.application.ports.input;

import com.lottery.validation.application.dto.FindLotteryDTO;
import com.lottery.validation.domain.entities.Lottery;
import org.springframework.data.domain.Page;

public interface FindLotteryInputPort {
    Page<Lottery> findLottery(FindLotteryDTO findLotteryDTO);
}
