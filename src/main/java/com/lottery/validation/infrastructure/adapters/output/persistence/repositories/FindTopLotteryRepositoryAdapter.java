package com.lottery.validation.infrastructure.adapters.output.persistence.repositories;

import java.util.List;

import org.springframework.stereotype.Component;

import com.lottery.validation.application.ports.output.FindTopLotteryOutputPort;
import com.lottery.validation.domain.entities.Lottery;
import com.lottery.validation.domain.enums.LotteryType;
import com.lottery.validation.infrastructure.adapters.output.persistence.entities.LotteryEntity;
import com.lottery.validation.infrastructure.adapters.output.persistence.mongodb.LotteryMongoRepository;

@Component
public class FindTopLotteryRepositoryAdapter implements FindTopLotteryOutputPort{

    private final LotteryMongoRepository lotteryMongoRepository;

    public FindTopLotteryRepositoryAdapter(LotteryMongoRepository lotteryMongoRepository) {
        this.lotteryMongoRepository = lotteryMongoRepository;
    }

    @Override
    public List<Lottery> findLotteries(LotteryType lotteryType) {
        var allByLotteryType = lotteryMongoRepository.findAllByLotteryType(lotteryType);
        return allByLotteryType.stream()
                .map(entity -> {
                    Lottery lottery = new Lottery();
                    lottery.setId(entity.getId());
                    lottery.setInternalId(entity.getInternalId());
                    lottery.setLotteryNumber(entity.getLotteryNumber());
                    lottery.setNextLotteryNumber(entity.getNextLotteryNumber());
                    lottery.setAddAt(entity.getAddAt());
                    lottery.setDrawDate(entity.getDrawDate());
                    lottery.setDrawnNumbers(entity.getDrawnNumbers());
                    lottery.setSortedDrawNumbers(entity.getSortedDrawNumbers());
                    lottery.setCity(entity.getCity());
                    lottery.setLotteryType(entity.getLotteryType());
                    return lottery;
                }).toList();
    }
    
}