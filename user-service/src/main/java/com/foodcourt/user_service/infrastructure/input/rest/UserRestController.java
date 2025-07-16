package com.foodcourt.user_service.infrastructure.input.rest;

import com.foodcourt.user_service.application.dto.request.UserRequestDto;
import com.foodcourt.user_service.application.handler.IUserHandler;
import com.foodcourt.user_service.infrastructure.exceptionhandler.dto.ExceptionResponse;
import com.foodcourt.user_service.infrastructure.exceptionhandler.dto.SuccessResponse;
import com.foodcourt.user_service.infrastructure.utils.InfrastructureConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@Tag(name = "User API", description = "Endpoints para la gestión de usuarios")
public class UserRestController {
    private final IUserHandler userHandler;

    @PostMapping("/owner")
    @Operation(summary = "Crear un nuevo Propietario",
            description = "Permite a un administrador crear una cuenta para un nuevo propietario de restaurante.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Propietario creado exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = SuccessResponse.class))),
            @ApiResponse(responseCode = "400", description = "Petición inválida, error en los datos de entrada",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Object.class))),
            @ApiResponse(responseCode = "409", description = "Conflicto, el usuario ya existe (documento o correo)",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class)))
    })
    public ResponseEntity<SuccessResponse> createOwner(@Valid @RequestBody UserRequestDto userRequestDto) {
        userHandler.createOwner(userRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new SuccessResponse(InfrastructureConstants.OWNER_CREATED_SUCCESSFULLY_MESSAGE));
    }
}
