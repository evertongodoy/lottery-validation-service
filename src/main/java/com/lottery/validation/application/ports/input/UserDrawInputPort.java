package com.lottery.validation.application.ports.input;

import com.lottery.validation.application.dto.UserDrawRequestDTO;
import com.lottery.validation.application.dto.UserDrawResponseDTO;

public interface UserDrawInputPort {

    UserDrawResponseDTO createUserDraw(UserDrawRequestDTO userDrawRequestDTO);

}
