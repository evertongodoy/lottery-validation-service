package com.lottery.validation.infrastructure.adapters.output.persistence.mongodb;

import com.lottery.validation.domain.enums.LotteryType;
import com.lottery.validation.infrastructure.adapters.output.persistence.entities.LotteryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LotteryMongoRepository extends MongoRepository<LotteryEntity, String> {
    
    Page<LotteryEntity> findByLotteryType(LotteryType lotteryType, Pageable pageable);
    
    Optional<LotteryEntity> findTopByLotteryTypeOrderByLotteryNumberDesc(LotteryType lotteryType);
    
    List<LotteryEntity> findAllByLotteryType(LotteryType lotteryType);
    
}
