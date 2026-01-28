package com.lottery.validation.infrastructure.adapters.output.persistence.repositories;

import com.lottery.validation.application.ports.output.SaveLotteryOutputPort;
import lombok.extern.slf4j.Slf4j;
import com.lottery.validation.domain.entities.Lottery;
import com.lottery.validation.domain.enums.LotteryType;
import com.lottery.validation.infrastructure.adapters.output.persistence.mappers.SaveLotteryPersistenceMapper;
import com.lottery.validation.infrastructure.adapters.output.persistence.mongodb.LotteryMongoRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
public class SaveLotteryRepositoryAdapter implements SaveLotteryOutputPort {

    private final LotteryMongoRepository lotteryMongoRepository;
    private final SaveLotteryPersistenceMapper saveLotteryPersistenceMapper;

    public SaveLotteryRepositoryAdapter(LotteryMongoRepository lotteryMongoRepository,
                                        SaveLotteryPersistenceMapper saveLotteryPersistenceMapper) {
        this.lotteryMongoRepository = lotteryMongoRepository;
        this.saveLotteryPersistenceMapper = saveLotteryPersistenceMapper;
    }

    @Override
    public Lottery save(Lottery lottery) {
        log.info("[save] | lottery={}", lottery);
        var entity = saveLotteryPersistenceMapper.toEntity(lottery);
        var savedEntity = lotteryMongoRepository.save(entity);
        return saveLotteryPersistenceMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Lottery> findTopByLotteryTypeOrderByLotteryNumberDesc(LotteryType lotteryType) {
        log.info("[findTopByLotteryTypeOrderByLotteryNumberDesc] | lotteryType={}", lotteryType);
        return lotteryMongoRepository.findTopByLotteryTypeOrderByLotteryNumberDesc(lotteryType)
                .map(saveLotteryPersistenceMapper::toDomain);
    }
}
