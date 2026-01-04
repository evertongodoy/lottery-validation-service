package com.lottery.validation.application.ports.output;

import java.util.Optional;
import java.util.UUID;

import com.lottery.validation.domain.entities.User;

public interface UserOutputPort {
    User save(User user);
    Optional<User> findBySubject(String subject);
    Optional<User> findByUuid(UUID uuid);
    boolean existsBySubject(String subject);
}
