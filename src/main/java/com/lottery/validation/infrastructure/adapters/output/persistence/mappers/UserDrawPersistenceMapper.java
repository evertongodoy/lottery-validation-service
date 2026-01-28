package com.lottery.validation.infrastructure.adapters.output.persistence.mappers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import com.lottery.validation.domain.entities.UserDraw;
import com.lottery.validation.infrastructure.adapters.output.persistence.entities.UserDrawEntity;

@Slf4j
@Component
public class UserDrawPersistenceMapper {

    public UserDrawEntity toEntity(UserDraw userDraw) {
        log.info("[toEntity] | userDraw={}", userDraw);
        UserDrawEntity entity = new UserDrawEntity();
        entity.setUuidDraw(userDraw.getUuidDraw());
        entity.setDrawNumbers(userDraw.getDrawNumbers());
        entity.setLotteryType(userDraw.getLotteryType());
        entity.setUuidSubject(userDraw.getUuidSubject());
        entity.setActive(userDraw.getActive());
        entity.setAddAt(userDraw.getAddAt());
        return entity;
    }

    public UserDraw toDomain(UserDrawEntity entity) {
        log.info("[toDomain] | entity={}", entity);
        UserDraw userDraw = new UserDraw();
        userDraw.setUuidDraw(entity.getUuidDraw());
        userDraw.setDrawNumbers(entity.getDrawNumbers());
        userDraw.setLotteryType(entity.getLotteryType());
        userDraw.setUuidSubject(entity.getUuidSubject());
        userDraw.setActive(entity.getActive());
        userDraw.setAddAt(entity.getAddAt());
        return userDraw;
    }
}
