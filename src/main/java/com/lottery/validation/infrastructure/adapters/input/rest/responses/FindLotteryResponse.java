package com.lottery.validation.infrastructure.adapters.input.rest.responses;

import com.lottery.validation.domain.entities.Lottery;
import com.lottery.validation.domain.enums.LotteryType;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class FindLotteryResponse {

    private List<LotteryData> content;
    private Integer page;
    private Integer size;
    private Long totalElements;
    private Integer totalPages;
    private Boolean last;

    public FindLotteryResponse() {
    }

    public FindLotteryResponse(List<LotteryData> content, Integer page, Integer size, 
                               Long totalElements, Integer totalPages, Boolean last) {
        this.content = content;
        this.page = page;
        this.size = size;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.last = last;
    }

    @Data
    public static class LotteryData {
        private String id;
        private Integer lotteryNumber;
        private Integer nextLotteryNumber;
        private LocalDate addAt;
        private LocalDate drawDate;
        private List<Integer> drawnNumbers;
        private List<Integer> sortedDrawNumbers;
        private String city;
        private LotteryType lotteryType;

        public LotteryData() {
        }

        public LotteryData(Lottery lottery) {
            this.id = lottery.getId();
            this.lotteryNumber = lottery.getLotteryNumber();
            this.nextLotteryNumber = lottery.getNextLotteryNumber();
            this.addAt = lottery.getAddAt();
            this.drawDate = lottery.getDrawDate();
            this.drawnNumbers = lottery.getDrawnNumbers();
            this.sortedDrawNumbers = lottery.getSortedDrawNumbers();
            this.city = lottery.getCity();
            this.lotteryType = lottery.getLotteryType();
        }

    }
}
