package com.lottery.validation.infrastructure.adapters.input.rest.responses;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaveLotteryResponse {

    @Schema(description = "Date and time when the lottery data was added", example = "2025-12-18T10:30:00")
    private LocalDateTime processingDate;

    @Schema(description = "List of draw IDs that were saved", example = "[1, 2, 3, 4, 5]")
    private List<Integer> drawIds;

    @Schema(description = "Total count of draws saved", example = "5")
    private Integer drawCount;

}