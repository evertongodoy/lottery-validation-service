package com.lottery.validation.domain.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import com.lottery.validation.domain.enums.UserRole;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    private UUID uuid;
    private String name;
    private UserRole role;
    private String subject;
    private String cellphone;
    private Boolean active;
    private LocalDateTime createdAt;

}
