package com.lottery.validation.infrastructure.adapters.input.rest.responses;

import com.lottery.validation.domain.enums.LotteryType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VerifyUserDrawWinnerResponse {
    
    @Schema(description = "Total number of winners found", example = "3")
    private Integer totalWinners;
    
    @Schema(description = "Lottery type that was verified", example = "MEGASENA")
    private LotteryType lotteryType;
    
    @Schema(description = "Lottery draw number", example = "2750")
    private Integer lotteryNumber;
}
