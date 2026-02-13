package com.lottery.validation.infrastructure.adapters.input.rest.requests;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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

    @Size(max = 255, message = "Note must not exceed 255 characters")
    @Pattern(regexp = "^[a-zA-Z0-9\\s.,;:!?()\"'\\-áàâãéèêíïóôõöúçñÁÀÂÃÉÈÊÍÏÓÔÕÖÚÇÑ]*$", 
             message = "Note contains invalid characters")
    private String note;

}
