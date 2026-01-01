package com.lottery.validation.infrastructure.adapters.output.persistence.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.lottery.validation.domain.enums.LotteryType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Document(collection = "user_draw")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDrawEntity {

    @Id
    private String id;
    
    private UUID uuidDraw;
    
    private List<Integer> drawNumbers;
    
    private LotteryType lotteryType;
    
    private UUID uuidSubject;
    
    private Boolean active;
    
    private LocalDate addAt;

}
