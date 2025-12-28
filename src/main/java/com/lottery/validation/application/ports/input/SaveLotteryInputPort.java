package com.lottery.validation.application.ports.input;

import com.lottery.validation.application.dto.SaveLotteryDTO;

import java.time.LocalDate;
import java.util.List;

public interface SaveLotteryInputPort {
    
    SaveLotteryResult saveLottery(SaveLotteryDTO saveLotteryDTO);

    record SaveLotteryResult(LocalDate processingDate, List<Integer> drawIds, Integer drawCount) {}
}
