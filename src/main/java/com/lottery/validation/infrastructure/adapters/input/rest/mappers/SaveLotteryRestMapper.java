package com.lottery.validation.infrastructure.adapters.input.rest.mappers;

import com.lottery.validation.application.dto.SaveLotteryDTO;
import com.lottery.validation.application.dto.SaveLotteryResultDTO;
import com.lottery.validation.infrastructure.adapters.input.rest.requests.RegisterLotteryRequest;
import com.lottery.validation.infrastructure.adapters.input.rest.responses.SaveLotteryResponse;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SaveLotteryRestMapper {

    public SaveLotteryDTO toDTO(RegisterLotteryRequest request, Integer waitTimeSeconds) {
        log.info("[toDTO] | request={}, waitTimeSeconds={}", request, waitTimeSeconds);
        if (Objects.isNull(request)) {
            return null;
        }
        return new SaveLotteryDTO(request.getLotteryType(), waitTimeSeconds);
    }

    public SaveLotteryResponse toResponse(SaveLotteryResultDTO result) {
        log.info("[toResponse] | result={}", result);
        if (Objects.isNull(result)) {
            return null;
        }
        return new SaveLotteryResponse(result.getProcessingDate(), result.getDrawIds(), result.getDrawCount());
    }
}
