package com.lottery.validation.application.usecases.lottery;

import com.lottery.validation.application.dto.FindLotteryDTO;
import com.lottery.validation.application.ports.input.FindLotteryInputPort;
import com.lottery.validation.application.ports.output.FindLotteryOutputPort;
import com.lottery.validation.domain.entities.Lottery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class FindLotteryUseCase implements FindLotteryInputPort {

    private final FindLotteryOutputPort findLotteryOutputPort;

    public FindLotteryUseCase(FindLotteryOutputPort findLotteryOutputPort) {
        this.findLotteryOutputPort = findLotteryOutputPort;
    }

    @Override
    public Page<Lottery> findLottery(FindLotteryDTO findLotteryDTO) {
        Sort.Direction sortDirection = "DESC".equalsIgnoreCase(findLotteryDTO.direction()) 
            ? Sort.Direction.DESC 
            : Sort.Direction.ASC;
        
        var pageable = PageRequest.of(
            findLotteryDTO.page(),
            findLotteryDTO.size(), 
            Sort.by(sortDirection, findLotteryDTO.orderBy()));
        
        return findLotteryOutputPort.findByLotteryType(findLotteryDTO.lotteryType(), pageable);
    }
}
