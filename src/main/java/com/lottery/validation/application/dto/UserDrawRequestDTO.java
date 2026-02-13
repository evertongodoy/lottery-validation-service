package com.lottery.validation.application.dto;

import java.time.LocalDate;
import java.util.List;

import com.lottery.validation.domain.enums.LotteryType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDrawRequestDTO {
    private List<Integer> drawNumbers;
    private LotteryType lotteryType;
    private String subject;
    private Boolean active;
    private LocalDate addAt;
    private String note;
}
