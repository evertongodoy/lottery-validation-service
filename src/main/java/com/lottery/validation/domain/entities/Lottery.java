package com.lottery.validation.domain.entities;

import com.lottery.validation.domain.enums.LotteryType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Lottery {
    private String id;
    private UUID internalId;
    private Integer lotteryNumber;
    private Integer nextLotteryNumber;
    private LocalDateTime addAt;
    private LocalDate drawDate;
    private List<Integer> drawnNumbers;
    private List<Integer> sortedDrawNumbers;
    private String city;
    private LotteryType lotteryType;
}
