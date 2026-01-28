package com.lottery.validation.infrastructure.adapters.output.persistence.repositories;

import com.lottery.validation.application.ports.output.VerifyUserDrawOutputPort;
import lombok.extern.slf4j.Slf4j;
import com.lottery.validation.domain.entities.UserDraw;
import com.lottery.validation.domain.entities.Winners;
import com.lottery.validation.domain.enums.LotteryType;
import com.lottery.validation.infrastructure.adapters.output.persistence.mappers.UserDrawPersistenceMapper;
import com.lottery.validation.infrastructure.adapters.output.persistence.mappers.WinnersUserDrawPersistenceMapper;
import com.lottery.validation.infrastructure.adapters.output.persistence.mongodb.UserDrawMongoRepository;
import com.lottery.validation.infrastructure.adapters.output.persistence.mongodb.WinnersUserDrawMongoRepository;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Component
public class VerifyUserDrawRepositoryAdapter implements VerifyUserDrawOutputPort {

    private static final String BASE_URL = "https://servicebus2.caixa.gov.br/portaldeloterias/api";
    private static final ParameterizedTypeReference<Map<String, Object>> MAP_TYPE = new ParameterizedTypeReference<>() {};

    private final UserDrawMongoRepository userDrawMongoRepository;
    private final UserDrawPersistenceMapper userDrawPersistenceMapper;
    private final WinnersUserDrawMongoRepository winnersUserDrawMongoRepository;
    private final WinnersUserDrawPersistenceMapper winnersUserDrawPersistenceMapper;
    private final WebClient webClient;

    public VerifyUserDrawRepositoryAdapter(UserDrawMongoRepository userDrawMongoRepository,
                                          UserDrawPersistenceMapper userDrawPersistenceMapper,
                                          WinnersUserDrawMongoRepository winnersUserDrawMongoRepository,
                                          WinnersUserDrawPersistenceMapper winnersUserDrawPersistenceMapper,
                                          WebClient.Builder webClientBuilder) {
        this.userDrawMongoRepository = userDrawMongoRepository;
        this.userDrawPersistenceMapper = userDrawPersistenceMapper;
        this.winnersUserDrawMongoRepository = winnersUserDrawMongoRepository;
        this.winnersUserDrawPersistenceMapper = winnersUserDrawPersistenceMapper;
        this.webClient = webClientBuilder.baseUrl(BASE_URL).build();
    }

    @Override
    public Map<String, Object> findWebLottery(LotteryType lotteryType) {
        log.info("[findWebLottery] | lotteryType={}", lotteryType);
        String lotteryPath = getLotteryPath(lotteryType);
        
        return Optional.ofNullable(
            webClient.get()
                .uri("/%s".formatted(lotteryPath))
                .retrieve()
                .bodyToMono(MAP_TYPE)
                .block()
        ).orElseThrow(() -> new RuntimeException("Failed to fetch latest draw from API"));
    }

    @Override
    public List<UserDraw> findActiveUserDrawsByLotteryType(LotteryType lotteryType) {
        log.info("[findActiveUserDrawsByLotteryType] | lotteryType={}", lotteryType);
        return userDrawMongoRepository.findAll()
                .stream()
                .filter(entity -> entity.getActive() != null && entity.getActive())
                .filter(entity -> entity.getLotteryType() == lotteryType)
                .map(userDrawPersistenceMapper::toDomain)
                .toList();
    }

    @Override
    public Winners saveWinners(Winners winners) {
        log.info("[saveWinners] | winners={}", winners);
        var entity = winnersUserDrawPersistenceMapper.toEntity(winners);
        var savedEntity = winnersUserDrawMongoRepository.save(entity);
        return winnersUserDrawPersistenceMapper.toDomain(savedEntity);
    }

    private String getLotteryPath(LotteryType lotteryType) {
        return switch (lotteryType) {
            case LOTOFACIL -> "lotofacil";
            case MEGASENA -> "megasena";
        };
    }
}
