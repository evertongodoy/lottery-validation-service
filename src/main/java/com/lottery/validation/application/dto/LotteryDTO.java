package com.lottery.validation.application.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.lottery.validation.domain.enums.LotteryType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LotteryDTO {
    
    private String id;
    private Integer lotteryNumber;
    private Integer nextLotteryNumber;
    private LocalDateTime addAt;
    private LocalDate drawDate;
    private List<Integer> drawnNumbers;
    private List<Integer> sortedDrawNumbers;
    private String city;
    private LotteryType lotteryType;

}
