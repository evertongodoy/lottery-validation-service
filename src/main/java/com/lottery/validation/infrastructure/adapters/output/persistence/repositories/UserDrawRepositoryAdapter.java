package com.lottery.validation.infrastructure.adapters.output.persistence.repositories;

import org.springframework.stereotype.Component;

import com.lottery.validation.application.ports.output.UserDrawOutputPort;
import com.lottery.validation.domain.entities.UserDraw;
import com.lottery.validation.infrastructure.adapters.output.persistence.mappers.UserDrawPersistenceMapper;
import com.lottery.validation.infrastructure.adapters.output.persistence.mongodb.UserDrawMongoRepository;

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
        var entity = userDrawPersistenceMapper.toEntity(userDraw);
        var savedEntity = userDrawMongoRepository.save(entity);
        return userDrawPersistenceMapper.toDomain(savedEntity);
    }
}
