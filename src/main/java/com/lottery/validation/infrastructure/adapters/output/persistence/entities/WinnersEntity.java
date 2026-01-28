package com.lottery.validation.infrastructure.adapters.output.persistence.entities;

import com.lottery.validation.domain.enums.LotteryType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Document(collection = "historic_winners")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WinnersEntity {

    @Id
    private String id;
    
    private UUID uuidDraw;
    
    private LotteryType lotteryType;
    
    private Integer lotteryNumber;
    
    private List<Integer> drawNumbers;
    
    private Integer totalMatches;
    
    private LocalDateTime verifiedAt;
    
    private List<Integer> numbersMatched;

    private Boolean messageSent;

}
