package com.lottery.validation.infrastructure.adapters.output.persistence.repositories;

import org.springframework.stereotype.Component;

import com.lottery.validation.application.ports.output.FindTopLotteryOutputPort;
import com.lottery.validation.domain.enums.LotteryType;

@Component
public class FindTopLotteryRepositoryAdapter implements FindTopLotteryOutputPort{

    @Override
    public void findLotteries(LotteryType lotteryType) {
        // TODO Auto-generated method stub
        System.out.println("FindTopLotteryRepositoryAdapter - findLotteries");
    }
    
}