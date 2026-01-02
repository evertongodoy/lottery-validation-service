package com.lottery.validation.application.ports.output;

import java.util.List;
import java.util.UUID;

import com.lottery.validation.domain.entities.UserDraw;

public interface UserDrawOutputPort {

    UserDraw save(UserDraw userDraw);
    List<UserDraw> findByUuidSubject(UUID uuidSubject);

}