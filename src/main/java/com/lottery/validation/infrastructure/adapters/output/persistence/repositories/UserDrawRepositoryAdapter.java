package com.lottery.validation.infrastructure.adapters.output.persistence.repositories;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

import com.lottery.validation.application.ports.output.UserDrawOutputPort;
import com.lottery.validation.domain.entities.UserDraw;
import com.lottery.validation.infrastructure.adapters.output.persistence.mappers.UserDrawPersistenceMapper;
import com.lottery.validation.infrastructure.adapters.output.persistence.mongodb.UserDrawMongoRepository;

@Slf4j
@Component
public class UserDrawRepositoryAdapter implements UserDrawOutputPort {

    private final UserDrawMongoRepository userDrawMongoRepository;
    private final UserDrawPersistenceMapper userDrawPersistenceMapper;

    public UserDrawRepositoryAdapter(UserDrawMongoRepository userDrawMongoRepository, 
                                      UserDrawPersistenceMapper userDrawPersistenceMapper) {
        this.userDrawMongoRepository = userDrawMongoRepository;
        this.userDrawPersistenceMapper = userDrawPersistenceMapper;
    }

    @Override
    public UserDraw save(UserDraw userDraw) {
        log.info("[save] | userDraw={}", userDraw);
        var entity = userDrawPersistenceMapper.toEntity(userDraw);
        var savedEntity = userDrawMongoRepository.save(entity);
        return userDrawPersistenceMapper.toDomain(savedEntity);
    }

    @Override
    public List<UserDraw> findByUuidSubject(UUID uuidSubject) {
        log.info("[findByUuidSubject] | uuidSubject={}", uuidSubject);
        return userDrawMongoRepository.findByUuidSubject(uuidSubject)
                .stream()
                .map(userDrawPersistenceMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public UserDraw findUserDrawByUuid(UUID uuidDraw) {
        log.info("[findUserDrawByUuid] | uuidDraw={}", uuidDraw);
        return userDrawMongoRepository.findAll()
                .stream()
                .filter(entity -> entity.getUuidDraw().equals(uuidDraw))
                .findFirst()
                .map(userDrawPersistenceMapper::toDomain)
                .orElseThrow(() -> new RuntimeException("UserDraw não encontrado para UUID: " + uuidDraw));
    }
}
