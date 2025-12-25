package com.lottery.validation.infrastructure.adapters.input.rest.responses;

import java.time.LocalDateTime;
import java.util.UUID;

import com.lottery.validation.domain.enums.UserRole;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private UUID uuid;
    private String name;
    private String subject;
    private String cellphone;
    private LocalDateTime createdAt;
    private UserRole role;

}
