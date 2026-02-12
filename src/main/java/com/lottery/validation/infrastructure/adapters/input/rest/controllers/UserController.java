package com.lottery.validation.infrastructure.adapters.input.rest.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;

import com.lottery.validation.application.dto.FindMyDrawRequestDTO;
import com.lottery.validation.application.ports.input.FindMyDrawInputPort;
import com.lottery.validation.application.ports.input.UserDrawInputPort;
import com.lottery.validation.application.ports.input.UserInputPort;
import com.lottery.validation.domain.enums.LotteryType;
import com.lottery.validation.domain.enums.UserRole;
import com.lottery.validation.infrastructure.adapters.input.rest.annotations.RequireRole;
import com.lottery.validation.infrastructure.adapters.input.rest.mappers.FindMyDrawRestMapper;
import com.lottery.validation.infrastructure.adapters.input.rest.mappers.UserDrawRestMapper;
import com.lottery.validation.infrastructure.adapters.input.rest.mappers.UserRestMapper;
import com.lottery.validation.infrastructure.adapters.input.rest.requests.CreateUserDrawRequest;
import com.lottery.validation.infrastructure.adapters.input.rest.requests.CreateUserRequest;
import com.lottery.validation.infrastructure.adapters.input.rest.responses.FindMyDrawResponse;
import com.lottery.validation.infrastructure.adapters.input.rest.responses.UserDrawResponse;
import com.lottery.validation.infrastructure.adapters.input.rest.responses.UserResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "Users", description = "User management endpoints")
public class UserController {

    private final UserInputPort userInputPort;
    private final UserRestMapper userRestMapper;
    private final UserDrawInputPort userDrawInputPort;
    private final UserDrawRestMapper userDrawRestMapper;
    private final FindMyDrawInputPort findMyDrawInputPort;
    private final FindMyDrawRestMapper findMyDrawRestMapper;

    public UserController(UserInputPort createUserInputPort, 
                          UserRestMapper userRestMapper,
                          UserDrawInputPort userDrawInputPort,
                          UserDrawRestMapper userDrawRestMapper,
                          FindMyDrawInputPort findMyDrawInputPort,
                          FindMyDrawRestMapper findMyDrawRestMapper) {
        this.userInputPort = createUserInputPort;
        this.userRestMapper = userRestMapper;
        this.userDrawInputPort = userDrawInputPort;
        this.userDrawRestMapper = userDrawRestMapper;
        this.findMyDrawInputPort = findMyDrawInputPort;
        this.findMyDrawRestMapper = findMyDrawRestMapper;
    }

    @PostMapping
    @Operation(summary = "Create a new user", description = "Creates a new user in the lottery validation system")
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody CreateUserRequest request) {
        log.info("[createUser] Início - Request Body: {}", request);
        var userDTO = userRestMapper.toDTO(request, false);
        var createdUser = userInputPort.createUser(userDTO);
        var response = userRestMapper.toResponse(createdUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/subject/{subject}")
    @Operation(summary = "Get user by subject", description = "Retrieves a user by their subject identifier (ADMIN only)")
    @RequireRole(UserRole.ADMIN)
    public ResponseEntity<UserResponse> getUserBySubject(@PathVariable String subject, ServerWebExchange exchange) {
        log.info("[getUserBySubject] Início - PathVariable: subject={}", subject);
        var userDTO = userInputPort.getSubjectData(subject);
        var response = userRestMapper.toResponse(userDTO);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/create-my-draw")
    @Operation(summary = "Create user draw", description = "Creates a new lottery draw for a user")
    public ResponseEntity<UserDrawResponse> createUserDraw(@Valid @RequestBody CreateUserDrawRequest request) {
        log.info("[createUserDraw] Início - Request Body: {}", request);
        var userDrawDTO = userDrawRestMapper.toDTO(request);
        var createdUserDraw = userDrawInputPort.createUserDraw(userDrawDTO);
        var response = userDrawRestMapper.toResponse(createdUserDraw);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/my-draw/{lotteryType}")
    @Operation(summary = "Find my draws", description = "Retrieves user draws from database with pagination")
    public ResponseEntity<FindMyDrawResponse> findMyDraw(
            @PathVariable LotteryType lotteryType,
            @RequestHeader("X-Subject") String subject,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            @RequestParam(value = "orderBy", defaultValue = "addAt") String orderBy,
            @RequestParam(value = "direction", defaultValue = "DESC") String direction) {
        log.info("[findMyDraw] Início - PathVariable: lotteryType={} | Header X-Subject: {} | Params: page={}, size={}, orderBy={}, direction={}", 
                lotteryType, subject, page, size, orderBy, direction);
        FindMyDrawRequestDTO findMyDrawRequestDTO = new FindMyDrawRequestDTO(lotteryType, page, size, orderBy, direction, subject);
        var resultDTO = findMyDrawInputPort.findMyDraw(findMyDrawRequestDTO);
        var response = findMyDrawRestMapper.toResponse(resultDTO);
        return ResponseEntity.ok(response);
    }

}