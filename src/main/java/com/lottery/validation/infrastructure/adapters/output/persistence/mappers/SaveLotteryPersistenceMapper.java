package com.lottery.validation.infrastructure.adapters.output.persistence.mappers;

import com.lottery.validation.domain.entities.Lottery;
import com.lottery.validation.infrastructure.adapters.output.persistence.entities.LotteryEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SaveLotteryPersistenceMapper {

    public LotteryEntity toEntity(Lottery lottery) {
        log.info("[toEntity] | lottery={}", lottery);
        if (lottery == null) {
            return null;
        }

        LotteryEntity entity = new LotteryEntity();
        entity.setId(lottery.getId());
        entity.setInternalId(lottery.getInternalId());
        entity.setLotteryNumber(lottery.getLotteryNumber());
        entity.setNextLotteryNumber(lottery.getNextLotteryNumber());
        entity.setAddAt(lottery.getAddAt());
        entity.setDrawDate(lottery.getDrawDate());
        entity.setDrawnNumbers(lottery.getDrawnNumbers());
        entity.setSortedDrawNumbers(lottery.getSortedDrawNumbers());
        entity.setCity(lottery.getCity());
        entity.setLotteryType(lottery.getLotteryType());
        return entity;
    }

    public Lottery toDomain(LotteryEntity entity) {
        log.info("[toDomain] | entity={}", entity);
        if (entity == null) {
            return null;
        }

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
    }
}
