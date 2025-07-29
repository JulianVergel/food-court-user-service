package com.foodcourt.user_service.infrastructure.input.rest.controller;

import com.foodcourt.user_service.application.dto.request.UserRequestDto;
import com.foodcourt.user_service.application.dto.response.UserResponseDto;
import com.foodcourt.user_service.application.handler.IUserHandler;
import com.foodcourt.user_service.infrastructure.exceptionhandler.dto.SuccessResponse;
import com.foodcourt.user_service.infrastructure.input.rest.doc.ICreateUserApiResponses;
import com.foodcourt.user_service.infrastructure.utils.InfrastructureConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@Tag(name = InfrastructureConstants.SWAGGER_TAG_USER_API_NAME,
        description = InfrastructureConstants.SWAGGER_TAG_USER_API_DESCRIPTION)
public class UserRestController {
    private final IUserHandler userHandler;

    @PostMapping("/owner")
    @Operation(summary = InfrastructureConstants.SUMMARY_CREATE_OWNER)
    @ICreateUserApiResponses
    @PreAuthorize("hasAuthority('ROLE_Administrador')")
    public ResponseEntity<SuccessResponse> createOwner(@Valid @RequestBody UserRequestDto userRequestDto) {
        userHandler.createOwner(userRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new SuccessResponse(InfrastructureConstants.OWNER_CREATED_SUCCESSFULLY_MESSAGE));
    }

    @GetMapping("/{id}")
    @Operation(summary = InfrastructureConstants.SUMMARY_GET_USER_BY_ID)
    @ICreateUserApiResponses
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userHandler.getUserById(id));
    }

    @PostMapping("/employee")
    @Operation(summary = InfrastructureConstants.SUMMARY_CREATE_EMPLOYEE)
    @ICreateUserApiResponses
    @PreAuthorize("hasAuthority('ROLE_Propietario')")
    public ResponseEntity<SuccessResponse> createEmployee(@Valid @RequestBody UserRequestDto userRequestDto) {
        userHandler.createEmployee(userRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new SuccessResponse(InfrastructureConstants.EMPLOYEE_CREATED_SUCCESSFULLY_MESSAGE));
    }

    @PostMapping("/client")
    @Operation(summary = InfrastructureConstants.SUMMARY_CREATE_CUSTOMER)
    @ICreateUserApiResponses
    public ResponseEntity<SuccessResponse> createClient(@Valid @RequestBody UserRequestDto userRequestDto) {
        userHandler.createClient(userRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new SuccessResponse(InfrastructureConstants.CUSTOMER_CREATED_SUCCESSFULLY_MESSAGE));
    }
}
