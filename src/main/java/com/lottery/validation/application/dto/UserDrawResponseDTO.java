package com.lottery.validation.application.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import com.lottery.validation.domain.enums.LotteryType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDrawResponseDTO {
    private UUID uuidDraw;
    private List<Integer> drawNumbers;
    private LotteryType lotteryType;
    private UUID uuidSubject;
    private Boolean active;
    private LocalDate addAt;
}
