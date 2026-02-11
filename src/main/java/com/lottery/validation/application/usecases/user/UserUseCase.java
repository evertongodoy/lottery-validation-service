package com.lottery.validation.application.usecases.user;

import java.time.LocalDateTime;
import java.util.UUID;

import com.lottery.validation.application.dto.UserDTO;
import com.lottery.validation.application.ports.input.UserInputPort;
import com.lottery.validation.application.ports.output.UserOutputPort;
import com.lottery.validation.domain.entities.User;
import com.lottery.validation.domain.exceptions.DuplicateSubjectException;

import lombok.extern.slf4j.Slf4j;

// Nao precisa do @Service porque a classe ja e criada no BeanConfiguration
@Slf4j
public class UserUseCase implements UserInputPort {

    private final UserOutputPort userOutputPort;

    public UserUseCase(UserOutputPort userOutputPort) {
        this.userOutputPort = userOutputPort;
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        log.info("[createUser] | userDTO={}", userDTO);
        // Validar se o subject já existe
        if (userOutputPort.existsBySubject(userDTO.getSubject().trim())) {
            throw new DuplicateSubjectException(userDTO.getSubject());
        }

        // Criar entidade de domínio
        var user = User.builder()
            .uuid(UUID.randomUUID())
            .name(userDTO.getName())
            .role(userDTO.getRole())
            .subject(userDTO.getSubject())
            .cellphone(userDTO.getCellphone())
            .password(userDTO.getPassword())
            .active(userDTO.getActive())
            .createdAt(LocalDateTime.now()).build();

        // Salvar usuário
        var savedUser = userOutputPort.save(user);
        
        // Retornar DTO
        return UserDTO.builder()
            .uuid(savedUser.getUuid())
            .name(savedUser.getName())
            .role(savedUser.getRole())
            .subject(savedUser.getSubject())
            .cellphone(savedUser.getCellphone())
            .password(savedUser.getPassword())
            .active(savedUser.getActive())
            .createdAt(savedUser.getCreatedAt())
        .build();
    }

    @Override
    public UserDTO getSubjectData(String subject) {
        log.info("[getSubjectData] | subject={}", subject);
        // Find user by subject
        var user = userOutputPort.findBySubject(subject)
                .orElseThrow(() -> new RuntimeException("Subject informado nao existe"));

        return UserDTO.builder()
            .uuid(user.getUuid())
            .name(user.getName())
            .role(user.getRole())
            .subject(user.getSubject())
            .cellphone(user.getCellphone())
            .password(user.getPassword())
            .active(user.getActive())
            .createdAt(user.getCreatedAt())
        .build();
    }
}
