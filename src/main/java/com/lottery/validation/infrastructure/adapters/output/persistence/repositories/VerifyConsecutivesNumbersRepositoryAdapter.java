package com.lottery.validation.infrastructure.adapters.output.persistence.repositories;

import com.lottery.validation.application.ports.output.VerifyConsecutivesNumbersOutputPort;
import com.lottery.validation.domain.entities.Lottery;
import com.lottery.validation.domain.enums.LotteryType;
import com.lottery.validation.infrastructure.adapters.output.persistence.mappers.SaveLotteryPersistenceMapper;
import com.lottery.validation.infrastructure.adapters.output.persistence.mongodb.LotteryMongoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class VerifyConsecutivesNumbersRepositoryAdapter implements VerifyConsecutivesNumbersOutputPort {

    private final LotteryMongoRepository lotteryMongoRepository;
    private final SaveLotteryPersistenceMapper saveLotteryPersistenceMapper;

    public VerifyConsecutivesNumbersRepositoryAdapter(LotteryMongoRepository lotteryMongoRepository,
                                                      SaveLotteryPersistenceMapper saveLotteryPersistenceMapper) {
        this.lotteryMongoRepository = lotteryMongoRepository;
        this.saveLotteryPersistenceMapper = saveLotteryPersistenceMapper;
    }

    @Override
    public List<Lottery> findLotteries(LotteryType lotteryType) {
        log.info("[findLotteries] Buscando todas as loterias do tipo: {}", lotteryType);
        List<Lottery> lotteries = lotteryMongoRepository.findAllByLotteryType(lotteryType)
                .stream()
                .map(saveLotteryPersistenceMapper::toDomain)
                .collect(Collectors.toList());
        log.info("[findLotteries] Total de loterias encontradas: {}", lotteries.size());
        return lotteries;
    }
}
