package com.lottery.validation.application.usecases.lottery;

import com.lottery.validation.application.dto.SaveLotteryDTO;
import com.lottery.validation.application.dto.SaveLotteryResultDTO;
import com.lottery.validation.application.ports.input.SaveLotteryInputPort;
import com.lottery.validation.application.ports.output.SaveLotteryOutputPort;
import com.lottery.validation.domain.entities.Lottery;
import com.lottery.validation.domain.enums.LotteryType;
import com.lottery.validation.infrastructure.config.LotteryApiProperties;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SaveLotteryUseCase implements SaveLotteryInputPort {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private final SaveLotteryOutputPort saveLotteryOutputPort;
    private final RestTemplate restTemplate;
    private final LotteryApiProperties lotteryApiProperties;

    public SaveLotteryUseCase(SaveLotteryOutputPort saveLotteryOutputPort, RestTemplate restTemplate, LotteryApiProperties lotteryApiProperties) {
        this.saveLotteryOutputPort = saveLotteryOutputPort;
        this.restTemplate = restTemplate;
        this.lotteryApiProperties = lotteryApiProperties;
    }

    @Override
    public SaveLotteryResultDTO saveLottery(SaveLotteryDTO saveLotteryDTO) {
        log.info("[saveLottery] | saveLotteryDTO={}", saveLotteryDTO);
        var lotteryType = saveLotteryDTO.getLotteryType();
        var lotteryPath = getLotteryPath(lotteryType);
        var waitTimeSeconds = saveLotteryDTO.getWaitTimeSeconds() != null ? saveLotteryDTO.getWaitTimeSeconds() : 3;
        
        // Buscar o último registro no banco de dados
        var nextDrawNumber = saveLotteryOutputPort
                .findTopByLotteryTypeOrderByLotteryNumberDesc(lotteryType)
                .map(Lottery::getNextLotteryNumber)
                .orElse(1);
        
        // Buscar o ultimo sorteio disponível na API
        var url = String.format("%s/%s", lotteryApiProperties.getUrl(), lotteryPath);
        var response = restTemplate.getForObject(url, Map.class);
        var latestDrawNumberFromApi = getIntegerValue(response, "numero");
        
        if (latestDrawNumberFromApi == null) {
            throw new RuntimeException("Failed to fetch latest draw number from API");
        }

        var processingDate = LocalDateTime.now();
        List<Integer> drawIds = new ArrayList<>();

        // Loop para buscar e salvar sorteios
        while (nextDrawNumber <= latestDrawNumberFromApi) {
            
            final Integer currentDrawNumber = nextDrawNumber;
            var drawUrl = String.format("%s/%s/%d", lotteryApiProperties.getUrl(), lotteryPath, currentDrawNumber);
            
            var lotteryData = restTemplate.getForObject(drawUrl, Map.class);
            
            if (lotteryData == null) {
                throw new RuntimeException("Failed to fetch draw data from API for draw number: " + currentDrawNumber);
            }

            var nextLotteryNumberFromApi = getIntegerValue(lotteryData, "numeroConcursoProximo");

            // Criar e salvar a entidade
            var lottery = createLotteryFromApiData(lotteryData, lotteryType, processingDate);
            var savedLottery = saveLotteryOutputPort.save(lottery);
            
            drawIds.add(savedLottery.getLotteryNumber());

            // Atualizar para o próximo sorteio
            nextDrawNumber = nextLotteryNumberFromApi;
            
            // Parar quando o próximo jogo for maior que o último disponível na API
            if(nextDrawNumber > latestDrawNumberFromApi){
                break;
            }
            
            // Aguardar antes da próxima requisição para evitar sobrecarga na API
            LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(waitTimeSeconds));
        }

        return new SaveLotteryResultDTO(processingDate, drawIds, drawIds.size());
    }

    private Lottery createLotteryFromApiData(Map<String, Object> data, LotteryType lotteryType, LocalDateTime addAt) {
        Lottery lottery = new Lottery();
        lottery.setInternalId(UUID.randomUUID());
        lottery.setLotteryNumber(getIntegerValue(data, "numero"));
        lottery.setNextLotteryNumber(getIntegerValue(data, "numeroConcursoProximo"));
        lottery.setAddAt(addAt);
        lottery.setDrawDate(parseDate(data.get("dataApuracao")));
        lottery.setDrawnNumbers(extractNumbers(data, "dezenasSorteadasOrdemSorteio"));
        lottery.setSortedDrawNumbers(extractNumbers(data, "listaDezenas"));
        lottery.setCity((String) data.get("nomeMunicipioUFSorteio"));
        lottery.setLotteryType(lotteryType);
        return lottery;
    }

    private String getLotteryPath(LotteryType lotteryType) {
        return switch (lotteryType) {
            case LOTOFACIL -> "lotofacil";
            case MEGASENA -> "megasena";
        };
    }

    private LocalDate parseDate(Object dateObj) {
        if (dateObj == null) {
            return null;
        }
        try {
            return LocalDate.parse(dateObj.toString(), DATE_FORMATTER);
        } catch (Exception e) {
            return null;
        }
    }

    private List<Integer> extractNumbers(Map<String, Object> data, String key) {
        Object value = data.get(key);
        if (value instanceof List) {
            List<?> list = (List<?>) value;
            List<Integer> numbers = new ArrayList<>();
            for (Object item : list) {
                if (item instanceof Map) {
                    Map<?, ?> map = (Map<?, ?>) item;
                    Object numero = map.get("numero");
                    if (numero != null) {
                        numbers.add(Integer.parseInt(numero.toString()));
                    }
                } else if (item instanceof Number) {
                    numbers.add(((Number) item).intValue());
                } else if (item instanceof String) {
                    numbers.add(Integer.parseInt((String) item));
                }
            }
            return numbers;
        }
        return new ArrayList<>();
    }

    private Integer getIntegerValue(Map<String, Object> data, String key) {
        Object value = data.get(key);
        if (value instanceof Number) {
            return ((Number) value).intValue();
        } else if (value instanceof String) {
            return Integer.parseInt((String) value);
        }
        return null;
    }
}
