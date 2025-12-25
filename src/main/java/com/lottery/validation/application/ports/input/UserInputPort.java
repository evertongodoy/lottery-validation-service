package com.lottery.validation.application.ports.input;

import com.lottery.validation.application.dto.UserDTO;

public interface UserInputPort {

    UserDTO createUser(UserDTO userDTO);

}
