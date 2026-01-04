package com.lottery.validation.application.usecases.user;

import com.lottery.validation.application.ports.input.SendVerifiedUserDrawInputPort;
import com.lottery.validation.application.ports.output.SendVerifiedUserDrawOutputPort;
import com.lottery.validation.application.ports.output.UserOutputPort;
import com.lottery.validation.domain.entities.UserDraw;
import com.lottery.validation.domain.entities.Winners;
import com.lottery.validation.domain.enums.LotteryType;

// Nao precisa do @Service porque a classe ja e criada no BeanConfiguration
public class SendVerifiedUserDrawUseCase implements SendVerifiedUserDrawInputPort {

    private final SendVerifiedUserDrawOutputPort sendVerifiedUserDrawOutputPort;
    private final UserOutputPort userOutputPort;

    public SendVerifiedUserDrawUseCase(SendVerifiedUserDrawOutputPort sendVerifiedUserDrawOutputPort,
                                      UserOutputPort userOutputPort) {
        this.sendVerifiedUserDrawOutputPort = sendVerifiedUserDrawOutputPort;
        this.userOutputPort = userOutputPort;
    }

    @Override
    public void sendVerifiedWinnerDraw(LotteryType lotteryType) {

        // Buscar todos os ganhadores do tipo de loteria
        var winners = sendVerifiedUserDrawOutputPort.findWinners(lotteryType);
        
        // Para cada ganhador, buscar informações do usuário e logar
        for (Winners winner : winners) {
            // Buscar o UserDraw pelo uuidDraw
            UserDraw userDraw = sendVerifiedUserDrawOutputPort.findUserDrawByUuid(winner.getUuidDraw());
            
            // Buscar o usuário pelo uuidSubject (UUID)
            var userOptional = userOutputPort.findByUuid(userDraw.getUuidSubject());
            
            if (userOptional.isPresent()) {
                var user = userOptional.get();
                
                // Log completo com todas as informações
                System.out.println("\n>>> GANHADOR ENCONTRADO <<<");
                System.out.println("UUID Draw: " + winner.getUuidDraw());
                System.out.println("Tipo Loteria: " + winner.getLotteryType());
                System.out.println("Número Sorteio: " + winner.getLotteryNumber());
                System.out.println("Números Jogados: " + winner.getDrawNumbers());
                System.out.println("Total de Acertos: " + winner.getTotalMatches());
                System.out.println("Números Acertados: " + winner.getNumbersMatched());
                System.out.println("Verificado em: " + winner.getVerifiedAt());
                System.out.println("--- Dados do Usuário ---");
                System.out.println("Nome: " + user.getName());
                System.out.println("Celular: " + user.getCellphone());
                System.out.println("Subject: " + user.getSubject());
                System.out.println("========================\n");
            } else {
                System.out.println("ATENÇÃO: Usuário não encontrado para o UUID: " + userDraw.getUuidSubject());
            }
        }
        
        System.out.println("=== Envio de mensagens concluído ===");
    }
}
