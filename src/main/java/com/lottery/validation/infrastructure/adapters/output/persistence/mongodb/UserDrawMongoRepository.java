package com.lottery.validation.infrastructure.adapters.output.persistence.mongodb;

import java.util.List;
import java.util.UUID;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.lottery.validation.infrastructure.adapters.output.persistence.entities.UserDrawEntity;

@Repository
public interface UserDrawMongoRepository extends MongoRepository<UserDrawEntity, String> {
    
    List<UserDrawEntity> findByUuidSubject(UUID uuidSubject);

}
