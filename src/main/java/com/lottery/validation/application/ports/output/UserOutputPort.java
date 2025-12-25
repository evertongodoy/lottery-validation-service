package com.lottery.validation.application.ports.output;

import java.util.Optional;

import com.lottery.validation.domain.entities.User;

public interface UserOutputPort {
    User save(User user);
    Optional<User> findBySubject(String subject);
    boolean existsBySubject(String subject);
}
