package com.lottery.validation.application.usecases.lottery;

import com.lottery.validation.application.ports.input.FindTopLotteryInputPort;
import com.lottery.validation.application.ports.output.FindTopLotteryOutputPort;
import com.lottery.validation.domain.enums.LotteryType;

// Nao precisa do @Service porque a classe ja e criada no BeanConfiguration
public class FindTopLotteryUseCase implements FindTopLotteryInputPort {

    private final FindTopLotteryOutputPort findTopLotteryOutputPort;

    public FindTopLotteryUseCase(FindTopLotteryOutputPort findTopLotteryOutputPort) {
        this.findTopLotteryOutputPort = findTopLotteryOutputPort;
    }
    
    @Override
    public void findTopLottery(LotteryType lotteryType) {
        findTopLotteryOutputPort.findLotteries(lotteryType);
        System.out.println("Finding top lottery...");
    }

}
