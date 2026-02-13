package com.lottery.validation.domain.entities;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import com.lottery.validation.domain.enums.LotteryType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDraw {
    private UUID uuidDraw;
    private List<Integer> drawNumbers;
    private LotteryType lotteryType;
    private UUID uuidSubject;
    private Boolean active;
    private LocalDate addAt;
    private String note;
}
