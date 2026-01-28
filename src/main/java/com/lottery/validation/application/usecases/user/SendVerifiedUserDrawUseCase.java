package com.lottery.validation.application.usecases.user;

import com.lottery.validation.application.ports.input.SendVerifiedUserDrawInputPort;
import com.lottery.validation.application.ports.output.SendVerifiedUserDrawOutputPort;
import com.lottery.validation.application.ports.output.UserOutputPort;
import com.lottery.validation.domain.entities.UserDraw;
import com.lottery.validation.domain.entities.Winners;
import com.lottery.validation.domain.enums.LotteryType;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

import lombok.extern.slf4j.Slf4j;

// Nao precisa do @Service porque a classe ja e criada no BeanConfiguration
@Slf4j
public class SendVerifiedUserDrawUseCase implements SendVerifiedUserDrawInputPort {

    private static final String WHATSAPP_API_URL = "https://evolution-evolution-api.kdzex0.easypanel.host";
    private static final String API_KEY = "429683C4C977415CAAFCCE10F7D57E11";

    private final SendVerifiedUserDrawOutputPort sendVerifiedUserDrawOutputPort;
    private final UserOutputPort userOutputPort;
    private final WebClient webClient;

    public SendVerifiedUserDrawUseCase(SendVerifiedUserDrawOutputPort sendVerifiedUserDrawOutputPort,
                                      UserOutputPort userOutputPort,
                                      WebClient.Builder webClientBuilder) {
        this.sendVerifiedUserDrawOutputPort = sendVerifiedUserDrawOutputPort;
        this.userOutputPort = userOutputPort;
        this.webClient = webClientBuilder.baseUrl(WHATSAPP_API_URL).build();
    }

    @Override
    public void sendVerifiedWinnerDraw(LotteryType lotteryType) {
        log.info("[sendVerifiedWinnerDraw] | lotteryType={}", lotteryType);

        // Buscar todos os ganhadores do tipo de loteria
        var winners = sendVerifiedUserDrawOutputPort.findWinners(lotteryType);
        
        // Para cada ganhador, buscar informações do usuário e logar
        for (Winners winner : winners) {
            // Buscar o UserDraw pelo uuidDraw
            UserDraw userDraw = sendVerifiedUserDrawOutputPort.findUserDrawByUuid(winner.getUuidDraw());
            
            // Buscar o usuário pelo uuidSubject (UUID)
            var user = userOutputPort.findByUuid(userDraw.getUuidSubject())
                .orElseThrow(() -> new RuntimeException("Usuario não encontrado para o UUID: " + userDraw.getUuidSubject()));
        
            var textMessage = String.format(
                "🤑 Parabéns *%s*! Você está entre os ganhadores do sorteio *%d* da loteria *%s* com *%d* acertos 🎯! Números acertados: *%s* 💰",
                user.getName(),
                winner.getLotteryNumber(),
                winner.getLotteryType().name(),
                winner.getTotalMatches(),
                winner.getNumbersMatched().toString()
            );

            try {
                var requestBody = Map.of(
                    "number", "55".concat(user.getCellphone()),
                    "text", textMessage,
                    "delay", 2000
                );

                webClient.post()
                    .uri("/message/sendText/teste2")
                    .header("apikey", API_KEY)
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            } catch (Exception e) {
                System.err.println("Erro ao enviar mensagem WhatsApp: " + e.getMessage());
            }
        }
    }
}
