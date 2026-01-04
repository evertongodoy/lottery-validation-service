package com.lottery.validation.infrastructure.adapters.output.persistence.mappers;

import com.lottery.validation.domain.entities.Winners;
import com.lottery.validation.infrastructure.adapters.output.persistence.entities.WinnersEntity;
import org.springframework.stereotype.Component;

@Component
public class WinnersUserDrawPersistenceMapper {

    public WinnersEntity toEntity(Winners winners) {
        return WinnersEntity.builder()
                .uuidDraw(winners.getUuidDraw())
                .lotteryType(winners.getLotteryType())
                .lotteryNumber(winners.getLotteryNumber())
                .drawNumbers(winners.getDrawNumbers())
                .totalMatches(winners.getTotalMatches())
                .verifiedAt(winners.getVerifiedAt())
                .numbersMatched(winners.getNumbersMatched())
                .build();
    }

    public Winners toDomain(WinnersEntity entity) {
        return Winners.builder()
                .uuidDraw(entity.getUuidDraw())
                .lotteryType(entity.getLotteryType())
                .lotteryNumber(entity.getLotteryNumber())
                .drawNumbers(entity.getDrawNumbers())
                .totalMatches(entity.getTotalMatches())
                .verifiedAt(entity.getVerifiedAt())
                .numbersMatched(entity.getNumbersMatched())
                .build();
    }
}
