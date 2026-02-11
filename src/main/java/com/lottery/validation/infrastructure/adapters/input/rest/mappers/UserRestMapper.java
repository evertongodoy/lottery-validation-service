package com.lottery.validation.infrastructure.adapters.input.rest.mappers;

import lombok.extern.slf4j.Slf4j;

import java.util.Base64;

import org.springframework.stereotype.Component;

import com.lottery.validation.application.dto.UserDTO;
import com.lottery.validation.infrastructure.adapters.input.rest.requests.CreateUserRequest;
import com.lottery.validation.infrastructure.adapters.input.rest.responses.UserResponse;

@Slf4j
@Component
public class UserRestMapper {

    public UserDTO toDTO(CreateUserRequest request, Boolean active) {
        log.info("[toDTO] | request={}", request);
        UserDTO dto = new UserDTO();
        dto.setName(request.getName());
        dto.setRole(request.getRole());
        dto.setSubject(request.getSubject());
        dto.setCellphone(request.getCellphone());
        // Convert password to Base64 due to security reasons
        dto.setPassword(Base64.getEncoder().encodeToString(request.getPassword().getBytes()));
        dto.setActive(active);
        return dto;
    }

    public UserResponse toResponse(UserDTO dto) {
        log.info("[toResponse] | dto={}", dto);
        return new UserResponse(
            dto.getUuid(),
            dto.getName(),
            dto.getSubject(),
            dto.getCellphone(),
            dto.getPassword(),
            dto.getActive(),
            dto.getCreatedAt(),
            dto.getRole()
        );
    }
}
