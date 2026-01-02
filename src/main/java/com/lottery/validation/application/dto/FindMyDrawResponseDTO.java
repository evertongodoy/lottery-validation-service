package com.lottery.validation.application.dto;

import java.util.List;

import com.lottery.validation.domain.entities.UserDraw;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FindMyDrawResponseDTO {
    private List<UserDraw> content;
    private Integer page;
    private Integer size;
    private Long totalElements;
    private Integer totalPages;
    private Boolean last;
}
