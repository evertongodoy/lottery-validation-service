package com.lottery.validation.application.dto;

import com.lottery.validation.domain.enums.LotteryType;

import java.time.LocalDate;
import java.util.List;

public class FindLotteryResultDTO {

    private List<LotteryData> content;
    private Integer page;
    private Integer size;
    private Long totalElements;
    private Integer totalPages;
    private Boolean last;

    public FindLotteryResultDTO() {
    }

    public FindLotteryResultDTO(List<LotteryData> content, Integer page, Integer size,
                                Long totalElements, Integer totalPages, Boolean last) {
        this.content = content;
        this.page = page;
        this.size = size;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.last = last;
    }

    public List<LotteryData> getContent() {
        return content;
    }

    public void setContent(List<LotteryData> content) {
        this.content = content;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(Long totalElements) {
        this.totalElements = totalElements;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public Boolean getLast() {
        return last;
    }

    public void setLast(Boolean last) {
        this.last = last;
    }

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

        public LotteryData(String id, Integer lotteryNumber, Integer nextLotteryNumber,
                          LocalDate addAt, LocalDate drawDate, List<Integer> drawnNumbers,
                          List<Integer> sortedDrawNumbers, String city, LotteryType lotteryType) {
            this.id = id;
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
}
