package com.lottery.validation.application.usecases.user;

import com.lottery.validation.application.dto.WinnersDTO;
import com.lottery.validation.application.ports.input.VerifyUserDrawInputPort;
import com.lottery.validation.application.ports.output.VerifyUserDrawOutputPort;
import com.lottery.validation.domain.entities.UserDraw;
import com.lottery.validation.domain.entities.Winners;
import com.lottery.validation.domain.enums.LotteryType;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import lombok.extern.slf4j.Slf4j;

// Nao precisa do @Service porque a classe ja e criada no BeanConfiguration
@Slf4j
public class VerifyUserDrawUseCase implements VerifyUserDrawInputPort {

    private final VerifyUserDrawOutputPort verifyUserDrawOutputPort;

    public VerifyUserDrawUseCase(VerifyUserDrawOutputPort verifyUserDrawOutputPort) {
        this.verifyUserDrawOutputPort = verifyUserDrawOutputPort;
    }

    @Override
    public WinnersDTO verifyUserDraws(LotteryType lotteryType) {
        log.info("[verifyUserDraws] | lotteryType={}", lotteryType);
        var webLottery = verifyUserDrawOutputPort.findWebLottery(lotteryType);
        var lotteryNumber = webLottery.get("numero");
        var totalWinners = 0;
        var sortedDrawNumbers = extractNumbers(webLottery, "listaDezenas");

        var activeUserDraws = verifyUserDrawOutputPort.findActiveUserDrawsByLotteryType(lotteryType);

        int minMatches = lotteryType == LotteryType.MEGASENA ? 4 : 11;
        
        // Verificar cada jogo ativo
        for (UserDraw userDraw : activeUserDraws) {
            int matches = countMatches(userDraw.getDrawNumbers(), sortedDrawNumbers);
            
            var shouldStore = matches >= minMatches;
            
            if (shouldStore && Objects.isNull(verifyUserDrawOutputPort.findHistoricWinnersByUuidDrawAndLotteryNumber(lotteryType, Integer.parseInt(lotteryNumber.toString()), userDraw.getUuidDraw()))) {
                totalWinners++;
                
                // Calcular os números que coincidiram
                var numbersMatched = getMatchedNumbers(userDraw.getDrawNumbers(), sortedDrawNumbers);
                
                // Criar objeto Winners
                var winners = Winners.builder()
                        .uuidDraw(userDraw.getUuidDraw())
                        .lotteryType(lotteryType)
                        .lotteryNumber(Integer.parseInt(lotteryNumber.toString()))
                        .drawNumbers(userDraw.getDrawNumbers())
                        .totalMatches(matches)
                        .verifiedAt(LocalDateTime.now())
                        .numbersMatched(numbersMatched)
                        .messageSent(Boolean.FALSE)
                        .build();
                
                // Salvar o ganhador na collection historic_winners
                verifyUserDrawOutputPort.saveWinners(winners);
            }
        }
        return WinnersDTO.builder()
                .totalWinners(totalWinners)
                .lotteryType(lotteryType)
                .lotteryNumber(Integer.parseInt(lotteryNumber.toString()))
                .build();
    }
    
    private int countMatches(List<Integer> userNumbers, List<Integer> drawnNumbers) {
        int count = 0;
        for (Integer number : userNumbers) {
            if (drawnNumbers.contains(number)) {
                count++;
            }
        }
        return count;
    }
    
    private List<Integer> getMatchedNumbers(List<Integer> userNumbers, List<Integer> drawnNumbers) {
        List<Integer> matched = new ArrayList<>();
        for (Integer number : userNumbers) {
            if (drawnNumbers.contains(number)) {
                matched.add(number);
            }
        }
        return matched;
    }
    
    private List<Integer> extractNumbers(Map<String, Object> data, String key) {
        Object value = data.get(key);
        if (value instanceof List) {
            List<?> list = (List<?>) value;
            return list.stream()
                    .map(item -> {
                        if (item instanceof Map) {
                            Map<?, ?> map = (Map<?, ?>) item;
                            Object numero = map.get("numero");
                            if (numero != null) {
                                return Integer.parseInt(numero.toString());
                            }
                        } else if (item instanceof Number) {
                            return ((Number) item).intValue();
                        } else if (item instanceof String) {
                            return Integer.parseInt((String) item);
                        }
                        return null;
                    })
                    .filter(n -> n != null)
                    .toList();
        }
        return List.of();
    }
}
