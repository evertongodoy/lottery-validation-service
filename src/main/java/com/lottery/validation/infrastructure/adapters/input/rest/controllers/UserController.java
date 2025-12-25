package com.lottery.validation.infrastructure.adapters.input.rest.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lottery.validation.application.ports.input.UserInputPort;
import com.lottery.validation.infrastructure.adapters.input.rest.mappers.UserRestMapper;
import com.lottery.validation.infrastructure.adapters.input.rest.requests.CreateUserRequest;
import com.lottery.validation.infrastructure.adapters.input.rest.responses.UserResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "Users", description = "User management endpoints")
public class UserController {

    private final UserInputPort userInputPort;
    private final UserRestMapper userRestMapper;

    public UserController(UserInputPort createUserInputPort, UserRestMapper userRestMapper) {
        this.userInputPort = createUserInputPort;
        this.userRestMapper = userRestMapper;
    }

    @PostMapping
    @Operation(summary = "Create a new user", description = "Creates a new user in the lottery validation system")
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody CreateUserRequest request) {
        var userDTO = userRestMapper.toDTO(request);
        var createdUser = userInputPort.createUser(userDTO);
        var response = userRestMapper.toResponse(createdUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{subject}")
    @Operation(summary = "Get user by subject", description = "Retrieves a user by their subject identifier")
    public ResponseEntity<String> getUserBySubject(@PathVariable String subject) {
        return ResponseEntity.ok(subject); 
    }
}
