package com.lottery.validation.application.usecases.user;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.UUID;

import com.lottery.validation.application.dto.UserDrawRequestDTO;
import com.lottery.validation.application.dto.UserDrawResponseDTO;
import com.lottery.validation.application.ports.input.UserDrawInputPort;
import com.lottery.validation.application.ports.output.UserDrawOutputPort;
import com.lottery.validation.application.ports.output.UserOutputPort;
import com.lottery.validation.domain.entities.UserDraw;
import com.lottery.validation.domain.exceptions.DuplicateUserDrawException;
import com.lottery.validation.domain.exceptions.UserNotFoundException;

public class UserDrawUseCase implements UserDrawInputPort {

    private final UserDrawOutputPort userDrawOutputPort;
    private final UserOutputPort userOutputPort;

    public UserDrawUseCase(UserDrawOutputPort userDrawOutputPort, UserOutputPort userOutputPort) {
        this.userDrawOutputPort = userDrawOutputPort;
        this.userOutputPort = userOutputPort;
    }

    @Override
    public UserDrawResponseDTO createUserDraw(UserDrawRequestDTO userDrawRequestDTO) {
        // Buscar o UUID do usuário pelo subject
        var user = userOutputPort.findBySubject(userDrawRequestDTO.getSubject())
                .orElseThrow(() -> new UserNotFoundException(userDrawRequestDTO.getSubject()));
        
        // Recuperar apostas existentes do usuário
        var existingDraws = userDrawOutputPort.findByUuidSubject(user.getUuid());
        
        // Validar se já existe aposta com mesmo tipo de loteria e mesmos números
        var requestNumbersSet = new HashSet<>(userDrawRequestDTO.getDrawNumbers());
        
        boolean duplicateExists = existingDraws.stream()
                .anyMatch(draw -> 
                    draw.getLotteryType().equals(userDrawRequestDTO.getLotteryType()) &&
                    new HashSet<>(draw.getDrawNumbers()).equals(requestNumbersSet)
                );
        
        if (duplicateExists) {
            throw new DuplicateUserDrawException(userDrawRequestDTO.getLotteryType());
        }

        // Criar a entidade de domínio UserDraw
        var userDraw = new UserDraw();
        userDraw.setUuidDraw(UUID.randomUUID());
        userDraw.setDrawNumbers(userDrawRequestDTO.getDrawNumbers());
        userDraw.setLotteryType(userDrawRequestDTO.getLotteryType());
        userDraw.setUuidSubject(user.getUuid());
        userDraw.setActive(true);
        userDraw.setAddAt(LocalDate.now());

        // Salvar o jogo
        var savedUserDraw = userDrawOutputPort.save(userDraw);

        // Retornar o DTO de resposta
        return UserDrawResponseDTO.builder()
                .uuidDraw(savedUserDraw.getUuidDraw())
                .drawNumbers(savedUserDraw.getDrawNumbers())
                .lotteryType(savedUserDraw.getLotteryType())
                .uuidSubject(savedUserDraw.getUuidSubject())
                .active(savedUserDraw.getActive())
                .addAt(savedUserDraw.getAddAt())
                .build();
    }
}
