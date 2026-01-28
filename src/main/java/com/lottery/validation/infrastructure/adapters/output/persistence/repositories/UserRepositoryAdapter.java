package com.lottery.validation.infrastructure.adapters.output.persistence.repositories;

import org.springframework.stereotype.Component;

import com.lottery.validation.application.ports.output.UserOutputPort;
import lombok.extern.slf4j.Slf4j;
import com.lottery.validation.domain.entities.User;
import com.lottery.validation.infrastructure.adapters.output.persistence.mappers.UserPersistenceMapper;
import com.lottery.validation.infrastructure.adapters.output.persistence.mongodb.UserMongoRepository;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
public class UserRepositoryAdapter implements UserOutputPort {

    private final UserMongoRepository userMongoRepository;
    private final UserPersistenceMapper userPersistenceMapper;

    public UserRepositoryAdapter(UserMongoRepository userMongoRepository, UserPersistenceMapper userPersistenceMapper) {
        this.userMongoRepository = userMongoRepository;
        this.userPersistenceMapper = userPersistenceMapper;
    }

    @Override
    public User save(User user) {
        log.info("[save] | user={}", user);
        var entity = userPersistenceMapper.toEntity(user);
        var savedEntity = userMongoRepository.save(entity);
        return userPersistenceMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<User> findBySubject(String subject) {
        log.info("[findBySubject] | subject={}", subject);
        return userMongoRepository.findBySubject(subject)
                .map(userPersistenceMapper::toDomain);
    }

    @Override
    public Optional<User> findByUuid(UUID uuid) {
        log.info("[findByUuid] | uuid={}", uuid);
        return userMongoRepository.findByUuid(uuid)
                .map(userPersistenceMapper::toDomain);
    }

    @Override
    public boolean existsBySubject(String subject) {
        log.info("[existsBySubject] | subject={}", subject);
        return userMongoRepository.existsBySubject(subject);
    }
}
