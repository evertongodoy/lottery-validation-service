package com.lottery.validation.application.usecases.lottery;

import com.lottery.validation.application.dto.ConsecutiveNumbersDTO;
import com.lottery.validation.application.ports.input.VerifyConsecutivesNumbersInputPort;
import com.lottery.validation.application.ports.output.VerifyConsecutivesNumbersOutputPort;
import com.lottery.validation.domain.entities.Lottery;
import com.lottery.validation.domain.enums.LotteryType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class VerifyConsecutiveNumbersUseCase implements VerifyConsecutivesNumbersInputPort {

    private final VerifyConsecutivesNumbersOutputPort verifyConsecutivesNumbersOutputPort;

    public VerifyConsecutiveNumbersUseCase(VerifyConsecutivesNumbersOutputPort verifyConsecutivesNumbersOutputPort) {
        this.verifyConsecutivesNumbersOutputPort = verifyConsecutivesNumbersOutputPort;
    }

    @Override
    public ConsecutiveNumbersDTO verifyConsecutives(LotteryType lotteryType) {
        log.info("[verifyConsecutives] Início | lotteryType={}", lotteryType);
        
        // Buscar todas as loterias do tipo especificado e ordenar por número do sorteio
        List<Lottery> lotteries = verifyConsecutivesNumbersOutputPort.findLotteries(lotteryType);
        
        if (lotteries.isEmpty()) {
            log.warn("[verifyConsecutives] Nenhuma loteria encontrada para o tipo {}", lotteryType);
            return ConsecutiveNumbersDTO.builder()
                    .lotteryType(lotteryType)
                    .consecutiveAverage(new ArrayList<>())
                    .build();
        }
       
        
        // Ordenar por número do sorteio (do mais antigo para o mais recente)
        lotteries = lotteries.stream()
                .sorted(Comparator.comparing(Lottery::getLotteryNumber))
                .collect(Collectors.toList());
        
        log.info("[verifyConsecutives] Total de sorteios encontrados: {}", lotteries.size());
        
        // Determinar o range de números baseado no tipo de loteria
        int maxNumber = getMaxNumberForLotteryType(lotteryType);
        
        List<ConsecutiveNumbersDTO.ConsecutiveNumberStatistic> statistics = new ArrayList<>();
        
        // Para cada número possível, calcular as estatísticas
        for (int number = 1; number <= maxNumber; number++) {
            ConsecutiveNumbersDTO.ConsecutiveNumberStatistic statistic = calculateStatisticsForNumber(number, lotteries);
            statistics.add(statistic);
        }
        
        log.info("[verifyConsecutives] Estatísticas calculadas para {} números", statistics.size());
        
        return ConsecutiveNumbersDTO.builder()
                .lotteryType(lotteryType)
                .consecutiveAverage(statistics)
                .build();
    }
    
    private int getMaxNumberForLotteryType(LotteryType lotteryType) {
        switch (lotteryType) {
            case LOTOFACIL:
                return 25;
            case MEGASENA:
                return 60;
            default:
                log.warn("[getMaxNumberForLotteryType] Tipo de loteria desconhecido: {}", lotteryType);
                return 25; // Default
        }
    }
    
    private ConsecutiveNumbersDTO.ConsecutiveNumberStatistic calculateStatisticsForNumber(int number, List<Lottery> lotteries) {
        log.debug("[calculateStatisticsForNumber] Calculando estatísticas para o número {}", number);
        
        List<Integer> presentSequences = new ArrayList<>();
        List<Integer> absentSequences = new ArrayList<>();
        List<ConsecutiveNumbersDTO.DrawPresence> draws = new ArrayList<>();
        
        int currentPresentStreak = 0;
        int currentAbsentStreak = 0;
        boolean wasPresent = false;
        boolean firstOccurrence = true;
        
        for (Lottery lottery : lotteries) {
            boolean isPresent = lottery.getDrawnNumbers().contains(number);
            
            // Adicionar informação de presença/ausência para cada sorteio
            draws.add(ConsecutiveNumbersDTO.DrawPresence.builder()
                    .lotteryNumber(lottery.getLotteryNumber())
                    .present(isPresent ? "P" : "A")
                    .build());
            
            if (firstOccurrence) {
                // No primeiro sorteio, apenas inicializa o estado
                if (isPresent) {
                    currentPresentStreak = 1;
                    wasPresent = true;
                } else {
                    currentAbsentStreak = 1;
                    wasPresent = false;
                }
                firstOccurrence = false;
                continue;
            }
            
            if (isPresent) {
                if (wasPresent) {
                    // Continua a sequência de presença
                    currentPresentStreak++;
                } else {
                    // Terminou a sequência de ausência, registra e inicia nova sequência de presença
                    if (currentAbsentStreak > 0) {
                        absentSequences.add(currentAbsentStreak);
                    }
                    currentAbsentStreak = 0;
                    currentPresentStreak = 1;
                    wasPresent = true;
                }
            } else {
                if (!wasPresent) {
                    // Continua a sequência de ausência
                    currentAbsentStreak++;
                } else {
                    // Terminou a sequência de presença, registra e inicia nova sequência de ausência
                    if (currentPresentStreak > 0) {
                        presentSequences.add(currentPresentStreak);
                    }
                    currentPresentStreak = 0;
                    currentAbsentStreak = 1;
                    wasPresent = false;
                }
            }
        }
        
        // Registrar a última sequência
        if (currentPresentStreak > 0) {
            presentSequences.add(currentPresentStreak);
        }
        if (currentAbsentStreak > 0) {
            absentSequences.add(currentAbsentStreak);
        }
        
        // Calcular médias
        double presentAverage = presentSequences.isEmpty() ? 0.0 : 
                presentSequences.stream().mapToInt(Integer::intValue).average().orElse(0.0);
        
        double absentAverage = absentSequences.isEmpty() ? 0.0 : 
                absentSequences.stream().mapToInt(Integer::intValue).average().orElse(0.0);
        
        // Calcular máximos
        int maxConsecutivePresent = presentSequences.isEmpty() ? 0 :
                presentSequences.stream().mapToInt(Integer::intValue).max().orElse(0);
        
        int maxConsecutiveAbsent = absentSequences.isEmpty() ? 0 :
                absentSequences.stream().mapToInt(Integer::intValue).max().orElse(0);
        
        // Calcular mínimos
        int minConsecutivePresent = presentSequences.isEmpty() ? 0 :
                presentSequences.stream().mapToInt(Integer::intValue).min().orElse(0);
        
        int minConsecutiveAbsent = absentSequences.isEmpty() ? 0 :
                absentSequences.stream().mapToInt(Integer::intValue).min().orElse(0);
        
        int presentIntervals = presentSequences.size();
        int absentIntervals = absentSequences.size();
        
        log.debug("[calculateStatisticsForNumber] Número {} - Present: {} (max: {}, min: {}, intervalos: {}), Absent: {} (max: {}, min: {}, intervalos: {})", 
                number, String.format("%.2f", presentAverage), maxConsecutivePresent, minConsecutivePresent, presentIntervals, 
                String.format("%.2f", absentAverage), maxConsecutiveAbsent, minConsecutiveAbsent, absentIntervals);
        
        return ConsecutiveNumbersDTO.ConsecutiveNumberStatistic.builder()
                .number(number)
                .consecutivePresentAverage(Math.round(presentAverage * 100.0) / 100.0) // Arredondar para 2 casas decimais
                .maxConsecutivePresent(maxConsecutivePresent)
                .minConsecutivePresent(minConsecutivePresent)
                .presentIntervals(presentIntervals)
                .consecutiveAbsentAverage(Math.round(absentAverage * 100.0) / 100.0) // Arredondar para 2 casas decimais
                .maxConsecutiveAbsent(maxConsecutiveAbsent)
                .minConsecutiveAbsent(minConsecutiveAbsent)
                .absentIntervals(absentIntervals)
                .draws(draws)
                .build();
    }
}
