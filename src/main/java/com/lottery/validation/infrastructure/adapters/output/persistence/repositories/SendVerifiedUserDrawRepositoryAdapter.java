package com.lottery.validation.infrastructure.adapters.output.persistence.repositories;

import com.lottery.validation.application.ports.output.SendVerifiedUserDrawOutputPort;
import lombok.extern.slf4j.Slf4j;
import com.lottery.validation.domain.entities.UserDraw;
import com.lottery.validation.domain.entities.Winners;
import com.lottery.validation.domain.enums.LotteryType;
import com.lottery.validation.domain.exceptions.UserNotFoundException;
import com.lottery.validation.infrastructure.adapters.output.persistence.mappers.UserDrawPersistenceMapper;
import com.lottery.validation.infrastructure.adapters.output.persistence.mappers.WinnersUserDrawPersistenceMapper;
import com.lottery.validation.infrastructure.adapters.output.persistence.mongodb.UserDrawMongoRepository;
import com.lottery.validation.infrastructure.adapters.output.persistence.mongodb.WinnersUserDrawMongoRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Slf4j
@Component
public class SendVerifiedUserDrawRepositoryAdapter implements SendVerifiedUserDrawOutputPort {

    private final WinnersUserDrawMongoRepository winnersUserDrawMongoRepository;
    private final WinnersUserDrawPersistenceMapper winnersUserDrawPersistenceMapper;
    private final UserDrawMongoRepository userDrawMongoRepository;
    private final UserDrawPersistenceMapper userDrawPersistenceMapper;

    public SendVerifiedUserDrawRepositoryAdapter(
            WinnersUserDrawMongoRepository winnersUserDrawMongoRepository,
            WinnersUserDrawPersistenceMapper winnersUserDrawPersistenceMapper,
            UserDrawMongoRepository userDrawMongoRepository,
            UserDrawPersistenceMapper userDrawPersistenceMapper) {
        this.winnersUserDrawMongoRepository = winnersUserDrawMongoRepository;
        this.winnersUserDrawPersistenceMapper = winnersUserDrawPersistenceMapper;
        this.userDrawMongoRepository = userDrawMongoRepository;
        this.userDrawPersistenceMapper = userDrawPersistenceMapper;
    }

    @Override
    public List<Winners> findWinners(LotteryType lotteryType) {
        log.info("[findWinners] | lotteryType={}", lotteryType);
        // Buscar todos os ganhadores do tipo de loteria que foram verificados hoje
        LocalDate today = LocalDate.now();
        
        return winnersUserDrawMongoRepository.findAll()
                .stream()
                .filter(entity -> entity.getLotteryType() == lotteryType)
                .filter(entity -> entity.getVerifiedAt() != null 
                        && entity.getVerifiedAt().toLocalDate().equals(today))
                .map(winnersUserDrawPersistenceMapper::toDomain)
                .toList();
    }

    @Override
    public UserDraw findUserDrawByUuid(UUID uuidDraw) {
        log.info("[findUserDrawByUuid] | uuidDraw={}", uuidDraw);
        return userDrawMongoRepository.findAll()
                .stream()
                .filter(entity -> entity.getUuidDraw().equals(uuidDraw))
                .findFirst()
                .map(userDrawPersistenceMapper::toDomain)
                .orElseThrow(() -> new UserNotFoundException("UserDraw não encontrado para UUID: " + uuidDraw));
    }
}
