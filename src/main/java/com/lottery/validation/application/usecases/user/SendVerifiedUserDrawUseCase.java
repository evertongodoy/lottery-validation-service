package com.lottery.validation.application.usecases.user;

import com.lottery.validation.application.ports.input.SendVerifiedUserDrawInputPort;
import com.lottery.validation.application.ports.output.SendVerifiedUserDrawOutputPort;
import com.lottery.validation.application.ports.output.UserDrawOutputPort;
import com.lottery.validation.application.ports.output.UserOutputPort;
import com.lottery.validation.domain.entities.UserDraw;
import com.lottery.validation.domain.entities.Winners;
import com.lottery.validation.domain.enums.LotteryType;
import com.lottery.validation.infrastructure.config.EvolutionApiProperties;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

import lombok.extern.slf4j.Slf4j;

// Nao precisa do @Service porque a classe ja e criada no BeanConfiguration
@Slf4j
public class SendVerifiedUserDrawUseCase implements SendVerifiedUserDrawInputPort {

    private final SendVerifiedUserDrawOutputPort sendVerifiedUserDrawOutputPort;
    private final UserDrawOutputPort userDrawOutputPort;
    private final UserOutputPort userOutputPort;
    private final RestTemplate restTemplate;
    private final EvolutionApiProperties evolutionApiProperties;

    public SendVerifiedUserDrawUseCase(SendVerifiedUserDrawOutputPort sendVerifiedUserDrawOutputPort,
                                      UserDrawOutputPort userDrawOutputPort,
                                      UserOutputPort userOutputPort,
                                      RestTemplate restTemplate,
                                      EvolutionApiProperties evolutionApiProperties) {
        this.sendVerifiedUserDrawOutputPort = sendVerifiedUserDrawOutputPort;
        this.userDrawOutputPort = userDrawOutputPort;
        this.userOutputPort = userOutputPort;
        this.restTemplate = restTemplate;
        this.evolutionApiProperties = evolutionApiProperties;
    }

    @Override
    public void sendVerifiedWinnerDraw(LotteryType lotteryType) {
        log.info("[sendVerifiedWinnerDraw] | lotteryType={}", lotteryType);

        // Buscar todos os ganhadores do tipo de loteria
        var winners = sendVerifiedUserDrawOutputPort.findWinnersNotSent(lotteryType);
        
        // Para cada ganhador, buscar informações do usuário e logar
        for (Winners winner : winners) {
            // Buscar o UserDraw pelo uuidDraw
            UserDraw userDraw = userDrawOutputPort.findUserDrawByUuid(winner.getUuidDraw());
            
            // Buscar o usuário pelo uuidSubject (UUID)
            var user = userOutputPort.findByUuid(userDraw.getUuidSubject())
                .orElseThrow(() -> new RuntimeException("Usuario não encontrado para o UUID: " + userDraw.getUuidSubject()));
        
            var textMessage = String.format(
                "🤑 Parabéns *%s*! Você está entre os ganhadores do sorteio *%d* da loteria *%s* com *%d* acertos 🎯! Números acertados: *%s* 💰",
                user.getName().toUpperCase(),
                winner.getLotteryNumber(),
                winner.getLotteryType().name(),
                winner.getTotalMatches(),
                winner.getNumbersMatched().toString()
            );

            try {
                Map<String, Object> requestBody = Map.of(
                    "number", "55".concat(user.getCellphone()),
                    "text", textMessage,
                    "delay", 2000
                );

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                headers.set("apikey", evolutionApiProperties.getKey());

                HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);
                
                String url = evolutionApiProperties.getUrl() + "/message/sendText/teste2";
                restTemplate.postForObject(url, request, String.class);

                sendVerifiedUserDrawOutputPort.updateMessageSentStatus(winner.getUuidDraw(), winner.getLotteryNumber(), Boolean.TRUE);

            } catch (Exception e) {
                log.error("Erro ao enviar mensagem WhatsApp: {}", e.getMessage(), e);
            }
        }
    }
}
