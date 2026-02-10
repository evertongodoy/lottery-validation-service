package com.lottery.validation.infrastructure.adapters.output.persistence.entities;

import com.lottery.validation.domain.enums.LotteryType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Document(collection = "lottery")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LotteryEntity {

    @Id
    private String id;

    @Field("internal_id")
    private UUID internalId;

    @Field("lottery_number")
    private Integer lotteryNumber;

    @Field("next_lottery_number")
    private Integer nextLotteryNumber;

    @Field("add_at")
    private LocalDate addAt;

    @Field("draw_date")
    private LocalDate drawDate;

    @Field("drawn_numbers")
    private List<Integer> drawnNumbers;

    @Field("sorted_draw_numbers")
    private List<Integer> sortedDrawNumbers;

    @Field("city")
    private String city;

    @Field("lottery_type")
    private LotteryType lotteryType;

}
