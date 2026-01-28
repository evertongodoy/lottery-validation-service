package com.lottery.validation.infrastructure.adapters.output.persistence.mongodb;

import com.lottery.validation.domain.enums.LotteryType;
import com.lottery.validation.infrastructure.adapters.output.persistence.entities.WinnersEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface WinnersUserDrawMongoRepository extends MongoRepository<WinnersEntity, String> {
    
    Optional<WinnersEntity> findByLotteryTypeAndLotteryNumberAndUuidDraw(LotteryType lotteryType, Integer lotteryNumber, UUID uuidDraw);
    Optional<WinnersEntity> findByUuidDrawAndLotteryNumber(UUID uuidDraw, Integer lotteryNumber);
    
}
