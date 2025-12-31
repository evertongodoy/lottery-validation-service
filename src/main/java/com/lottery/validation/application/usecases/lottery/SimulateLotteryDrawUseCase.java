package com.lottery.validation.application.usecases.lottery;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.lottery.validation.application.dto.LotteryMatchDTO;
import com.lottery.validation.application.dto.SimulateLotteryDrawDTO;
import com.lottery.validation.application.ports.input.SimulateLotteryDrawInputPort;
import com.lottery.validation.application.ports.output.SimulateLotteryDrawOutputPort;
import com.lottery.validation.domain.entities.Lottery;
import com.lottery.validation.domain.enums.LotteryType;

public class SimulateLotteryDrawUseCase implements SimulateLotteryDrawInputPort {

    private final SimulateLotteryDrawOutputPort simulateLotteryDrawOutputPort;

    public SimulateLotteryDrawUseCase(SimulateLotteryDrawOutputPort simulateLotteryDrawOutputPort) {
        this.simulateLotteryDrawOutputPort = simulateLotteryDrawOutputPort;
    }

    @Override
    public SimulateLotteryDrawDTO simulateLotteryDraw(LotteryType lotteryType, List<Integer> numbers) {
        List<Lottery> lotteries = simulateLotteryDrawOutputPort.findLotteries(lotteryType);
        
        // Definir o mínimo de acertos conforme o tipo de jogo
        int minMatches = getMinMatchesForLotteryType(lotteryType);
        
        // Lista para armazenar os matches que atendem o critério
        List<LotteryMatchDTO> matches = new ArrayList<>();
        
        // Para cada sorteio, comparar com os números do usuário
        for (Lottery lottery : lotteries) {
            List<Integer> drawnNumbers = lottery.getSortedDrawNumbers();
            
            // Encontrar os números que deram match com este sorteio específico
            List<Integer> matchedNumbers = numbers.stream()
                    .filter(drawnNumbers::contains)
                    .sorted()
                    .collect(Collectors.toList());
            
            int totalMatches = matchedNumbers.size();
            
            // Se atingiu o mínimo de acertos, adicionar à lista
            if (totalMatches >= minMatches) {
                matches.add(LotteryMatchDTO.builder()
                        .lotteryNumber(lottery.getLotteryNumber())
                        .drawDate(lottery.getDrawDate())
                        .totalMatches(totalMatches)
                        .drawNumbers(matchedNumbers)
                        .build());
            }
        }
        
        // Ordenar por totalMatches (decrescente) e, em caso de empate, por drawDate (mais recente primeiro)
        matches.sort((m1, m2) -> {
            int matchesComparison = Integer.compare(m2.getTotalMatches(), m1.getTotalMatches());
            if (matchesComparison != 0) {
                return matchesComparison;
            }
            return m2.getDrawDate().compareTo(m1.getDrawDate());
        });
        
        return SimulateLotteryDrawDTO.builder()
                .lotteryType(lotteryType)
                .matches(matches)
                .build();
    }
    
    private int getMinMatchesForLotteryType(LotteryType lotteryType) {
        return switch (lotteryType) {
            case LOTOFACIL -> 11;
            case MEGASENA -> 4;
            default -> 9999; // High value to indicate unsupported type
        };
    }
}
