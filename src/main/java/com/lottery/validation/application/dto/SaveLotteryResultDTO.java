package com.lottery.validation.application.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaveLotteryResultDTO {

    private LocalDate processingDate;
    private List<Integer> drawIds;
    private Integer drawCount;

}