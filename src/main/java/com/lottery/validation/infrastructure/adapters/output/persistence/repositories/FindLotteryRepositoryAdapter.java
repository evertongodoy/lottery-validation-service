package com.lottery.validation.infrastructure.adapters.output.persistence.repositories;

import com.lottery.validation.application.ports.output.FindLotteryOutputPort;
import com.lottery.validation.domain.entities.Lottery;
import com.lottery.validation.domain.enums.LotteryType;
import com.lottery.validation.infrastructure.adapters.output.persistence.mappers.SaveLotteryPersistenceMapper;
import com.lottery.validation.infrastructure.adapters.output.persistence.mongodb.LotteryMongoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class FindLotteryRepositoryAdapter implements FindLotteryOutputPort {

    private final LotteryMongoRepository lotteryMongoRepository;
    private final SaveLotteryPersistenceMapper saveLotteryPersistenceMapper;

    public FindLotteryRepositoryAdapter(LotteryMongoRepository lotteryMongoRepository,
                                        SaveLotteryPersistenceMapper saveLotteryPersistenceMapper) {
        this.lotteryMongoRepository = lotteryMongoRepository;
        this.saveLotteryPersistenceMapper = saveLotteryPersistenceMapper;
    }

    @Override
    public Page<Lottery> findByLotteryType(LotteryType lotteryType, Pageable pageable) {
        return lotteryMongoRepository.findByLotteryType(lotteryType, pageable)
                .map(saveLotteryPersistenceMapper::toDomain);
    }
}
