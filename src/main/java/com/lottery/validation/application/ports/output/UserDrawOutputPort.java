package com.lottery.validation.application.ports.output;

import com.lottery.validation.domain.entities.UserDraw;

public interface UserDrawOutputPort {

    UserDraw save(UserDraw userDraw);

}
