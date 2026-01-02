package com.lottery.validation.domain.exceptions;

import com.lottery.validation.domain.enums.LotteryType;

public class DuplicateUserDrawException extends BusinessException {
    
    public DuplicateUserDrawException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicateUserDrawException(LotteryType lotteryType) {
        super(String.format("Já existe uma aposta cadastrada para o tipo de loteria '%s' com os mesmos números para o mesmo usuário.", lotteryType));
    }
}
