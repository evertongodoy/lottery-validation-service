package com.lottery.validation.infrastructure.adapters.output.persistence.mongodb;

import com.lottery.validation.domain.enums.LotteryType;
import com.lottery.validation.infrastructure.adapters.output.persistence.entities.LotteryEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SaveLotteryMongoRepository extends MongoRepository<LotteryEntity, String> {
    
    Optional<LotteryEntity> findTopByLotteryTypeOrderByLotteryNumberDesc(LotteryType lotteryType);
}
