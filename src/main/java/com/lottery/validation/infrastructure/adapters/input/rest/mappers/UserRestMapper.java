package com.lottery.validation.infrastructure.adapters.input.rest.mappers;

import org.springframework.stereotype.Component;

import com.lottery.validation.application.dto.UserDTO;
import com.lottery.validation.infrastructure.adapters.input.rest.requests.CreateUserRequest;
import com.lottery.validation.infrastructure.adapters.input.rest.responses.UserResponse;

@Component
public class UserRestMapper {

    public UserDTO toDTO(CreateUserRequest request) {
        UserDTO dto = new UserDTO();
        dto.setName(request.getName());
        dto.setRole(request.getRole());
        dto.setSubject(request.getSubject());
        dto.setCellphone(request.getCellphone());
        return dto;
    }

    public UserResponse toResponse(UserDTO dto) {
        return new UserResponse(
            dto.getUuid(),
            dto.getName(),
            dto.getSubject(),
            dto.getCellphone(),
            dto.getCreatedAt(),
            dto.getRole()
        );
    }
}
