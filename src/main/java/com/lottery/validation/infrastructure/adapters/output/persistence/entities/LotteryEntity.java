package com.lottery.validation.infrastructure.adapters.output.persistence.entities;

import com.lottery.validation.domain.enums.LotteryType;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Document(collection = "lottery")
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

    public LotteryEntity() {
    }

    public LotteryEntity(String id, UUID internalId, Integer lotteryNumber, Integer nextLotteryNumber,
                         LocalDate addAt, LocalDate drawDate, List<Integer> drawnNumbers,
                         List<Integer> sortedDrawNumbers, String city, LotteryType lotteryType) {
        this.id = id;
        this.internalId = internalId;
        this.lotteryNumber = lotteryNumber;
        this.nextLotteryNumber = nextLotteryNumber;
        this.addAt = addAt;
        this.drawDate = drawDate;
        this.drawnNumbers = drawnNumbers;
        this.sortedDrawNumbers = sortedDrawNumbers;
        this.city = city;
        this.lotteryType = lotteryType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public UUID getInternalId() {
        return internalId;
    }

    public void setInternalId(UUID internalId) {
        this.internalId = internalId;
    }

    public Integer getLotteryNumber() {
        return lotteryNumber;
    }

    public void setLotteryNumber(Integer lotteryNumber) {
        this.lotteryNumber = lotteryNumber;
    }

    public Integer getNextLotteryNumber() {
        return nextLotteryNumber;
    }

    public void setNextLotteryNumber(Integer nextLotteryNumber) {
        this.nextLotteryNumber = nextLotteryNumber;
    }

    public LocalDate getAddAt() {
        return addAt;
    }

    public void setAddAt(LocalDate addAt) {
        this.addAt = addAt;
    }

    public LocalDate getDrawDate() {
        return drawDate;
    }

    public void setDrawDate(LocalDate drawDate) {
        this.drawDate = drawDate;
    }

    public List<Integer> getDrawnNumbers() {
        return drawnNumbers;
    }

    public void setDrawnNumbers(List<Integer> drawnNumbers) {
        this.drawnNumbers = drawnNumbers;
    }

    public List<Integer> getSortedDrawNumbers() {
        return sortedDrawNumbers;
    }

    public void setSortedDrawNumbers(List<Integer> sortedDrawNumbers) {
        this.sortedDrawNumbers = sortedDrawNumbers;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public LotteryType getLotteryType() {
        return lotteryType;
    }

    public void setLotteryType(LotteryType lotteryType) {
        this.lotteryType = lotteryType;
    }
}
