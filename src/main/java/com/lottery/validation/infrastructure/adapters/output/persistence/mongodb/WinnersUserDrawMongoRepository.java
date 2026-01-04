package com.lottery.validation.infrastructure.adapters.output.persistence.mongodb;

import com.lottery.validation.infrastructure.adapters.output.persistence.entities.WinnersEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WinnersUserDrawMongoRepository extends MongoRepository<WinnersEntity, String> {
    
}
