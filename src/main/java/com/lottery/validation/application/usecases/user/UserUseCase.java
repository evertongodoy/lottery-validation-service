package com.lottery.validation.application.usecases.user;

import java.time.LocalDateTime;
import java.util.UUID;

import com.lottery.validation.application.dto.UserDTO;
import com.lottery.validation.application.ports.input.UserInputPort;
import com.lottery.validation.application.ports.output.UserOutputPort;
import com.lottery.validation.domain.entities.User;
import com.lottery.validation.domain.exceptions.DuplicateSubjectException;

// Nao precisa do @Service porque a classe ja e criada no BeanConfiguration
public class UserUseCase implements UserInputPort {

    private final UserOutputPort userOutputPort;

    public UserUseCase(UserOutputPort userOutputPort) {
        this.userOutputPort = userOutputPort;
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        // Validar se o subject já existe
        if (userOutputPort.existsBySubject(userDTO.getSubject())) {
            throw new DuplicateSubjectException(userDTO.getSubject());
        }

        // Criar entidade de domínio
        var user = new User();
        user.setUuid(UUID.randomUUID());
        user.setName(userDTO.getName());
        user.setRole(userDTO.getRole());
        user.setSubject(userDTO.getSubject());
        user.setCellphone(userDTO.getCellphone());
        user.setCreatedAt(LocalDateTime.now());

        // Salvar usuário
        var savedUser = userOutputPort.save(user);
        
        // Retornar DTO
        return new UserDTO(
            savedUser.getUuid(),
            savedUser.getName(),
            savedUser.getRole(),
            savedUser.getSubject(),
            savedUser.getCellphone(),
            savedUser.getCreatedAt()
        );
    }

    @Override
    public UserDTO getSubjectData(String subject) {
        // Buscar usuário pelo subject
        var user = userOutputPort.findBySubject(subject)
                .orElseThrow(() -> new RuntimeException("Subject informado nao existe"));

        // Retornar DTO
        return new UserDTO(
            user.getUuid(),
            user.getName(),
            user.getRole(),
            user.getSubject(),
            user.getCellphone(),
            user.getCreatedAt()
        );
    }
}
