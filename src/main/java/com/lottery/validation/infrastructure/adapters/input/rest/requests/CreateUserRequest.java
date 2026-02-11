package com.lottery.validation.infrastructure.adapters.input.rest.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.lottery.validation.domain.enums.UserRole;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateUserRequest {

    @NotBlank(message = "Name is required")
    private String name;

    @NotNull(message = "Role is required")
    private UserRole role;

    @NotBlank(message = "Subject is required")
    private String subject;

    @NotBlank(message = "Cellphone is required")
    @Pattern(regexp = "^\\+?[1-9]\\d{1,14}$", message = "Invalid cellphone format")
    private String cellphone;

    @NotBlank(message = "Password is required")
    private String password;

}
