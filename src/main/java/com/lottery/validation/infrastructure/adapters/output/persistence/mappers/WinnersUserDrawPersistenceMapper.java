package com.lottery.validation.infrastructure.adapters.output.persistence.mappers;

import com.lottery.validation.domain.entities.Winners;
import com.lottery.validation.infrastructure.adapters.output.persistence.entities.WinnersEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class WinnersUserDrawPersistenceMapper {

    public WinnersEntity toEntity(Winners winners) {
        log.info("[toEntity] | winners={}", winners);
        return WinnersEntity.builder()
                .uuidDraw(winners.getUuidDraw())
                .lotteryType(winners.getLotteryType())
                .lotteryNumber(winners.getLotteryNumber())
                .drawNumbers(winners.getDrawNumbers())
                .totalMatches(winners.getTotalMatches())
                .verifiedAt(winners.getVerifiedAt())
                .numbersMatched(winners.getNumbersMatched())
                .messageSent(winners.getMessageSent())
                .build();
    }

    public Winners toDomain(WinnersEntity entity) {
        log.info("[toDomain] | entity={}", entity);
        return Winners.builder()
                .uuidDraw(entity.getUuidDraw())
                .lotteryType(entity.getLotteryType())
                .lotteryNumber(entity.getLotteryNumber())
                .drawNumbers(entity.getDrawNumbers())
                .totalMatches(entity.getTotalMatches())
                .verifiedAt(entity.getVerifiedAt())
                .numbersMatched(entity.getNumbersMatched())
                .messageSent(entity.getMessageSent())
                .build();
    }
}
