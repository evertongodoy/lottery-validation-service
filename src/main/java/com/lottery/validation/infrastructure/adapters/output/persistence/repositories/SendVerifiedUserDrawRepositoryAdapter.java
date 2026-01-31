package com.lottery.validation.infrastructure.adapters.output.persistence.repositories;

import com.lottery.validation.application.ports.output.SendVerifiedUserDrawOutputPort;
import lombok.extern.slf4j.Slf4j;
import com.lottery.validation.domain.entities.Winners;
import com.lottery.validation.domain.enums.LotteryType;
import com.lottery.validation.domain.exceptions.UserNotFoundException;
import com.lottery.validation.infrastructure.adapters.output.persistence.mappers.WinnersUserDrawPersistenceMapper;
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

    public SendVerifiedUserDrawRepositoryAdapter(
            WinnersUserDrawMongoRepository winnersUserDrawMongoRepository,
            WinnersUserDrawPersistenceMapper winnersUserDrawPersistenceMapper) {
        this.winnersUserDrawMongoRepository = winnersUserDrawMongoRepository;
        this.winnersUserDrawPersistenceMapper = winnersUserDrawPersistenceMapper;
    }

    @Override
    public List<Winners> findWinnersNotSent(LotteryType lotteryType) {
        log.info("[findWinners] | lotteryType={}", lotteryType);
        // Buscar todos os ganhadores do tipo de loteria que foram verificados hoje
        LocalDate today = LocalDate.now();
        
        return winnersUserDrawMongoRepository.findAll()
                .stream()
                .filter(entity -> entity.getLotteryType() == lotteryType)
                .filter(entity -> entity.getVerifiedAt().toLocalDate().equals(today))
                .filter(entity -> Boolean.FALSE.equals(entity.getMessageSent()))
                .map(winnersUserDrawPersistenceMapper::toDomain)
                .toList();
    }

    @Override
    public Winners updateMessageSentStatus(UUID uuidDraw, Integer lotteryNumber, Boolean messageSent) {
        log.info("[updateMessageSentStatus] | uuidDraw={} | messageSent={}", uuidDraw, messageSent);
        var winnerEntity = winnersUserDrawMongoRepository.findByUuidDrawAndLotteryNumber(uuidDraw, lotteryNumber)
            .orElseThrow(() -> new UserNotFoundException("Winners não encontrado para UUID: " + uuidDraw + " e lotteryNumber: " + lotteryNumber));
        winnerEntity.setMessageSent(messageSent);
        var updatedEntity = winnersUserDrawMongoRepository.save(winnerEntity);
        return winnersUserDrawPersistenceMapper.toDomain(updatedEntity);
    }
}
