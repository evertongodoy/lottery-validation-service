package com.lottery.validation.application.usecases.lottery;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.lottery.validation.application.dto.FindTopFrequencyDTO;
import com.lottery.validation.application.dto.LotteryDTO;
import com.lottery.validation.application.dto.NumbersRankDTO;
import com.lottery.validation.application.ports.input.FindTopLotteryInputPort;
import com.lottery.validation.application.ports.output.FindTopLotteryOutputPort;
import com.lottery.validation.domain.entities.Lottery;
import com.lottery.validation.domain.enums.LotteryType;

// Nao precisa do @Service porque a classe ja e criada no BeanConfiguration
public class FindTopLotteryUseCase implements FindTopLotteryInputPort {

    private final FindTopLotteryOutputPort findTopLotteryOutputPort;

    public record NumbersRank(int position, int value, long repetitions) {}

    public FindTopLotteryUseCase(FindTopLotteryOutputPort findTopLotteryOutputPort) {
        this.findTopLotteryOutputPort = findTopLotteryOutputPort;
    }
    
    @Override
    public FindTopFrequencyDTO findTopLottery(LotteryType lotteryType) {
        List<Lottery> lotteries = findTopLotteryOutputPort.findLotteries(lotteryType);
        var sortedDrawNumbers = lotteries.stream()
                .map(Lottery::getSortedDrawNumbers)
                .toList();
        
        // 1) Counting frequencies of each number
        Map<Integer, Long> frequencies = sortedDrawNumbers.stream()
                .flatMap(List::stream)
                .collect(Collectors.groupingBy(
                        Function.identity(),
                        Collectors.counting()
                ));
        
        // 2) Creating a ranking based on frequencies
        List<NumbersRank> ranking = frequencies.entrySet().stream()
                .sorted(Map.Entry.<Integer, Long>comparingByValue().reversed())
                .collect(ArrayList::new, 
                        (list, entry) -> list.add(new NumbersRank(
                                list.size() + 1, 
                                entry.getKey(), 
                                entry.getValue()
                        )),
                        ArrayList::addAll);

        // 3) Preparing the DTO response
        return FindTopFrequencyDTO.builder()
                .lotteryType(lotteryType)
                .totalDraws(sortedDrawNumbers.size())
                .numbersRank(
                        ranking.stream()
                               .map(rank -> new NumbersRankDTO(
                                       rank.position,
                                       rank.value,
                                       rank.repetitions)
                                ).toList()
                ).build();
    }

}