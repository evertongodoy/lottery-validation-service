package com.lottery.validation.infrastructure.adapters.input.rest.mappers;

import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.lottery.validation.application.dto.FindMyDrawResponseDTO;
import com.lottery.validation.domain.entities.UserDraw;
import com.lottery.validation.infrastructure.adapters.input.rest.responses.FindMyDrawResponse;
import com.lottery.validation.infrastructure.adapters.input.rest.responses.MyDrawData;

@Component
public class FindMyDrawRestMapper {

    public FindMyDrawResponse toResponse(FindMyDrawResponseDTO dto) {
        return FindMyDrawResponse.builder()
                .content(dto.getContent().stream()
                        .map(this::toMyDrawData)
                        .collect(Collectors.toList()))
                .page(dto.getPage())
                .size(dto.getSize())
                .totalElements(dto.getTotalElements())
                .totalPages(dto.getTotalPages())
                .last(dto.getLast())
                .build();
    }

    private MyDrawData toMyDrawData(UserDraw userDraw) {
        return MyDrawData.builder()
                .uuidDraw(userDraw.getUuidDraw())
                .drawNumbers(userDraw.getDrawNumbers())
                .lotteryType(userDraw.getLotteryType())
                .uuidSubject(userDraw.getUuidSubject())
                .active(userDraw.getActive())
                .addAt(userDraw.getAddAt())
                .build();
    }
}
