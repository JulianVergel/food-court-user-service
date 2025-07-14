package com.foodcourt.user_service.infrastructure.input.rest;

import com.foodcourt.user_service.application.dto.request.UserRequestDto;
import com.foodcourt.user_service.application.handler.IUserHandler;

import com.foodcourt.user_service.infrastructure.exceptionhandler.dto.SuccessResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/usuario")
@RequiredArgsConstructor
public class UserRestController {
    private final IUserHandler userHandler;

    @PostMapping("/propietario")
    public ResponseEntity<SuccessResponse> createOwner(@Valid @RequestBody UserRequestDto userRequestDto) {
        userHandler.createOwner(userRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new SuccessResponse("Propietario creado exitosamente"));
    }
}
