package com.lottery.validation.domain.entities;

import com.lottery.validation.domain.enums.LotteryType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Winners {
    private UUID uuidDraw;
    private LotteryType lotteryType;
    private Integer lotteryNumber;
    private List<Integer> drawNumbers;
    private Integer totalMatches;
    private LocalDateTime verifiedAt;
    private List<Integer> numbersMatched;
    private Boolean messageSent;
}
