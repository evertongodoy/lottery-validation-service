package com.lottery.validation.application.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaveLotteryResultDTO {

    private LocalDateTime processingDate;
    private List<Integer> drawIds;
    private Integer drawCount;

}