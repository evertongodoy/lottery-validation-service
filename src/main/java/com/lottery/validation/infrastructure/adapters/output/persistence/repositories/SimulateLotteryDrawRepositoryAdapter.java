package com.lottery.validation.infrastructure.adapters.output.persistence.repositories;

import java.util.List;
import java.util.stream.Collectors;

import com.lottery.validation.application.ports.output.SimulateLotteryDrawOutputPort;
import com.lottery.validation.domain.entities.Lottery;
import com.lottery.validation.domain.enums.LotteryType;
import com.lottery.validation.infrastructure.adapters.output.persistence.mappers.SaveLotteryPersistenceMapper;
import com.lottery.validation.infrastructure.adapters.output.persistence.mongodb.LotteryMongoRepository;
import org.springframework.stereotype.Component;

@Component
public class SimulateLotteryDrawRepositoryAdapter implements SimulateLotteryDrawOutputPort {

    private final LotteryMongoRepository lotteryMongoRepository;
    private final SaveLotteryPersistenceMapper saveLotteryPersistenceMapper;

    public SimulateLotteryDrawRepositoryAdapter(LotteryMongoRepository lotteryMongoRepository,
                                                SaveLotteryPersistenceMapper saveLotteryPersistenceMapper) {
        this.lotteryMongoRepository = lotteryMongoRepository;
        this.saveLotteryPersistenceMapper = saveLotteryPersistenceMapper;
    }

    @Override
    public List<Lottery> findLotteries(LotteryType lotteryType) {
        return lotteryMongoRepository.findAllByLotteryType(lotteryType)
                .stream()
                .map(saveLotteryPersistenceMapper::toDomain)
                .collect(Collectors.toList());
    }
}
