package com.lottery.validation.application.usecases.lottery;

import com.lottery.validation.application.dto.FindLotteryDTO;
import com.lottery.validation.application.dto.FindLotteryResultDTO;
import com.lottery.validation.application.ports.input.FindLotteryInputPort;
import com.lottery.validation.application.ports.output.FindLotteryOutputPort;
import com.lottery.validation.domain.entities.Lottery;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class FindLotteryUseCase implements FindLotteryInputPort {

    private final FindLotteryOutputPort findLotteryOutputPort;

    public FindLotteryUseCase(FindLotteryOutputPort findLotteryOutputPort) {
        this.findLotteryOutputPort = findLotteryOutputPort;
    }

    @Override
    public FindLotteryResultDTO findLottery(FindLotteryDTO findLotteryDTO) {
        Sort.Direction sortDirection = "DESC".equalsIgnoreCase(findLotteryDTO.direction()) 
            ? Sort.Direction.DESC 
            : Sort.Direction.ASC;
        
        var pageable = PageRequest.of(
            findLotteryDTO.page(),
            findLotteryDTO.size(), 
            Sort.by(sortDirection, findLotteryDTO.orderBy()));
        
        var lotteryPage = findLotteryOutputPort.findByLotteryType(findLotteryDTO.lotteryType(), pageable);
        
        return new FindLotteryResultDTO(
            lotteryPage.getContent().stream()
                .map(this::toLotteryData)
                .collect(Collectors.toList()),
            lotteryPage.getNumber(),
            lotteryPage.getSize(),
            lotteryPage.getTotalElements(),
            lotteryPage.getTotalPages(),
            lotteryPage.isLast()
        );
    }
    
    private FindLotteryResultDTO.LotteryData toLotteryData(Lottery lottery) {
        return new FindLotteryResultDTO.LotteryData(
            lottery.getId(),
            lottery.getLotteryNumber(),
            lottery.getNextLotteryNumber(),
            lottery.getAddAt(),
            lottery.getDrawDate(),
            lottery.getDrawnNumbers(),
            lottery.getSortedDrawNumbers(),
            lottery.getCity(),
            lottery.getLotteryType()
        );
    }
}
