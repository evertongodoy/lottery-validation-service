package com.lottery.validation.infrastructure.adapters.output.persistence.mongodb;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.lottery.validation.infrastructure.adapters.output.persistence.entities.UserEntity;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserMongoRepository extends MongoRepository<UserEntity, String> {
    Optional<UserEntity> findBySubject(String subject);
    Optional<UserEntity> findByUuid(UUID uuid);
    boolean existsBySubject(String subject);
}
