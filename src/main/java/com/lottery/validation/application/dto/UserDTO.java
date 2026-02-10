package com.lottery.validation.application.dto;

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
public class UserDTO {
    private UUID uuid;
    private String name;
    private UserRole role;
    private String subject;
    private String cellphone;
    private Boolean active;
    private LocalDateTime createdAt;

}
