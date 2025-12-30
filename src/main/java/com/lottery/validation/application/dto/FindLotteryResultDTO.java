package com.lottery.validation.application.dto;

import lombok.Data;
import java.util.List;

@Data
public class FindLotteryResultDTO {

    private List<LotteryDTO> content;
    private Integer page;
    private Integer size;
    private Long totalElements;
    private Integer totalPages;
    private Boolean last;

    public FindLotteryResultDTO() {
    }

    public FindLotteryResultDTO(List<LotteryDTO> content, Integer page, Integer size,
                                Long totalElements, Integer totalPages, Boolean last) {
        this.content = content;
        this.page = page;
        this.size = size;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.last = last;
    }

}
