package com.lottery.validation.infrastructure.adapters.input.rest.mappers;

import java.time.LocalDate;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import com.lottery.validation.application.dto.UserDrawRequestDTO;
import com.lottery.validation.application.dto.UserDrawResponseDTO;
import com.lottery.validation.infrastructure.adapters.input.rest.requests.CreateUserDrawRequest;
import com.lottery.validation.infrastructure.adapters.input.rest.responses.UserDrawResponse;

@Slf4j
@Component
public class UserDrawRestMapper {

    public UserDrawRequestDTO toDTO(CreateUserDrawRequest request) {
        log.info("[toDTO] | request={}", request);
        UserDrawRequestDTO dto = new UserDrawRequestDTO();
        dto.setDrawNumbers(request.getDrawNumbers());
        dto.setLotteryType(request.getLotteryType());
        dto.setSubject(request.getSubject());
        dto.setActive(true);
        dto.setAddAt(LocalDate.now());
        return dto;
    }

    public UserDrawResponse toResponse(UserDrawResponseDTO dto) {
        log.info("[toResponse] | dto={}", dto);
        return UserDrawResponse.builder()
                .uuidDraw(dto.getUuidDraw())
                .drawNumbers(dto.getDrawNumbers())
                .lotteryType(dto.getLotteryType())
                .uuidSubject(dto.getUuidSubject())
                .active(dto.getActive())
                .addAt(dto.getAddAt())
                .build();
    }
}
