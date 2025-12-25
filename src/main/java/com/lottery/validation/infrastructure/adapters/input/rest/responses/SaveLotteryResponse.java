package com.lottery.validation.infrastructure.adapters.input.rest.responses;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class SaveLotteryResponse {

    @Schema(description = "Date when the lottery data was added", example = "2025-12-18")
    private LocalDate processingDate;

    @Schema(description = "List of draw IDs that were saved", example = "[1, 2, 3, 4, 5]")
    private List<Integer> drawIds;

    @Schema(description = "Total count of draws saved", example = "5")
    private Integer drawCount;

    public SaveLotteryResponse() {
    }

    public SaveLotteryResponse(LocalDate processingDate, List<Integer> drawIds, Integer drawCount) {
        this.processingDate = processingDate;
        this.drawIds = drawIds;
        this.drawCount = drawCount;
    }

}