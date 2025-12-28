package com.lottery.validation.infrastructure.adapters.input.rest.mappers;

import com.lottery.validation.application.dto.SaveLotteryDTO;
import com.lottery.validation.application.dto.SaveLotteryResultDTO;
import com.lottery.validation.infrastructure.adapters.input.rest.requests.RegisterLotteryRequest;
import com.lottery.validation.infrastructure.adapters.input.rest.responses.SaveLotteryResponse;
import org.springframework.stereotype.Component;

@Component
public class SaveLotteryRestMapper {

    public SaveLotteryDTO toDTO(RegisterLotteryRequest request) {
        if (request == null) {
            return null;
        }
        return new SaveLotteryDTO(request.getLotteryType());
    }

    public SaveLotteryResponse toResponse(SaveLotteryResultDTO result) {
        if (result == null) {
            return null;
        }
        return new SaveLotteryResponse(result.getProcessingDate(), result.getDrawIds(), result.getDrawCount());
    }
}
