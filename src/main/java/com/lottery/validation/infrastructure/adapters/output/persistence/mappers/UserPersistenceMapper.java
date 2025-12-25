package com.lottery.validation.infrastructure.adapters.output.persistence.mappers;

import org.springframework.stereotype.Component;

import com.lottery.validation.domain.entities.User;
import com.lottery.validation.infrastructure.adapters.output.persistence.entities.UserEntity;

@Component
public class UserPersistenceMapper {

    public UserEntity toEntity(User user) {
        UserEntity entity = new UserEntity();
        entity.setUuid(user.getUuid());
        entity.setName(user.getName());
        entity.setRole(user.getRole());
        entity.setSubject(user.getSubject());
        entity.setCellphone(user.getCellphone());
        entity.setCreatedAt(user.getCreatedAt());
        return entity;
    }

    public User toDomain(UserEntity entity) {
        User user = new User();
        user.setUuid(entity.getUuid());
        user.setName(entity.getName());
        user.setRole(entity.getRole());
        user.setSubject(entity.getSubject());
        user.setCellphone(entity.getCellphone());
        user.setCreatedAt(entity.getCreatedAt());
        return user;
    }
}
