package com.lottery.validation.application.usecases.user;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.lottery.validation.application.dto.FindMyDrawRequestDTO;
import com.lottery.validation.application.dto.FindMyDrawResponseDTO;
import com.lottery.validation.application.ports.input.FindMyDrawInputPort;
import com.lottery.validation.application.ports.output.FindMyDrawOutputPort;
import com.lottery.validation.application.ports.output.UserOutputPort;
import com.lottery.validation.domain.exceptions.UserNotFoundException;

public class FindMyDrawUseCase implements FindMyDrawInputPort {

    private final FindMyDrawOutputPort findMyDrawOutputPort;
    private final UserOutputPort userOutputPort;

    public FindMyDrawUseCase(FindMyDrawOutputPort findMyDrawOutputPort, UserOutputPort userOutputPort) {
        this.findMyDrawOutputPort = findMyDrawOutputPort;
        this.userOutputPort = userOutputPort;
    }

    @Override
    public FindMyDrawResponseDTO findMyDraw(FindMyDrawRequestDTO findMyDrawRequestDTO) {
        // Buscar o UUID do usuário pelo subject
        var user = userOutputPort.findBySubject(findMyDrawRequestDTO.getSubject())
                .orElseThrow(() -> new UserNotFoundException(findMyDrawRequestDTO.getSubject()));
        
        // Criar o Pageable com os parâmetros de paginação e ordenação
        Sort.Direction sortDirection = Sort.Direction.fromString(findMyDrawRequestDTO.getDirection());
        Pageable pageable = PageRequest.of(
            findMyDrawRequestDTO.getPage(),
            findMyDrawRequestDTO.getSize(),
            Sort.by(sortDirection, findMyDrawRequestDTO.getOrderBy())
        );

        // Buscar os draws do usuário
        var page = findMyDrawOutputPort.findMyDrawByLotteryType(
            user.getUuid(),
            findMyDrawRequestDTO.getLotteryType(),
            pageable
        );

        // Construir o response DTO
        return FindMyDrawResponseDTO.builder()
                .content(page.getContent())
                .page(page.getNumber())
                .size(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .last(page.isLast())
                .build();
    }
}
