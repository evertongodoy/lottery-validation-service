package com.lottery.validation.infrastructure.adapters.input.rest.requests;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.lottery.validation.domain.enums.LotteryType;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateUserDrawRequest {

    @NotEmpty(message = "Draw numbers are required")
    private List<Integer> drawNumbers;

    @NotNull(message = "Lottery type is required")
    private LotteryType lotteryType;

    @NotBlank(message = "Subject is required")
    private String subject;

}
