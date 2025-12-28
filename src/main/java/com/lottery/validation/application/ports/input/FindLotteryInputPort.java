package com.lottery.validation.application.ports.input;

import com.lottery.validation.application.dto.FindLotteryDTO;
import com.lottery.validation.application.dto.FindLotteryResultDTO;

public interface FindLotteryInputPort {
    FindLotteryResultDTO findLottery(FindLotteryDTO findLotteryDTO);
}
