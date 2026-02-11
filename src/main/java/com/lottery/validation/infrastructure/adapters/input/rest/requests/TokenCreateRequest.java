package com.lottery.validation.infrastructure.adapters.input.rest.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TokenCreateRequest {

    @NotBlank(message = "Subject is required")
    private String subject;

    @NotBlank(message = "Password is required")
    private String password;

}
