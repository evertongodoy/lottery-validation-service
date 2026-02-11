package com.lottery.validation.application.ports.input;

import com.lottery.validation.application.dto.CreateTokenDTO;
import com.lottery.validation.application.dto.TokenDTO;

public interface TokenInputPort {

    TokenDTO createToken(CreateTokenDTO dto);

}
