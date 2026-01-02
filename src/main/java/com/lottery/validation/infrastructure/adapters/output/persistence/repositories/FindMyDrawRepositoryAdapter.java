package com.lottery.validation.infrastructure.adapters.output.persistence.repositories;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.lottery.validation.application.ports.output.FindMyDrawOutputPort;
import com.lottery.validation.domain.entities.UserDraw;
import com.lottery.validation.domain.enums.LotteryType;
import com.lottery.validation.infrastructure.adapters.output.persistence.mappers.UserDrawPersistenceMapper;
import com.lottery.validation.infrastructure.adapters.output.persistence.mongodb.UserDrawMongoRepository;

@Component
public class FindMyDrawRepositoryAdapter implements FindMyDrawOutputPort {

    private final UserDrawMongoRepository userDrawMongoRepository;
    private final UserDrawPersistenceMapper userDrawPersistenceMapper;

    public FindMyDrawRepositoryAdapter(UserDrawMongoRepository userDrawMongoRepository, 
                                        UserDrawPersistenceMapper userDrawPersistenceMapper) {
        this.userDrawMongoRepository = userDrawMongoRepository;
        this.userDrawPersistenceMapper = userDrawPersistenceMapper;
    }

    @Override
    public Page<UserDraw> findMyDrawByLotteryType(UUID uuidSubject, LotteryType lotteryType, Pageable pageable) {
        var pageEntities = userDrawMongoRepository.findByUuidSubjectAndLotteryType(uuidSubject, lotteryType, pageable);
        return pageEntities.map(userDrawPersistenceMapper::toDomain);
    }
}
