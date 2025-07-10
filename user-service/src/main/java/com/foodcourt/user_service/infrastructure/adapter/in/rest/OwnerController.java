package com.foodcourt.user_service.infrastructure.adapter.in.rest;

import com.foodcourt.user_service.application.command.CreateOwnerCommand;
import com.foodcourt.user_service.application.response.OwnerResponse;
import com.foodcourt.user_service.domain.port.in.CreateOwnerUseCase;
import com.foodcourt.user_service.infrastructure.adapter.in.rest.dto.CreateOwnerRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // Marca esta clase como un controlador REST de Spring
@RequestMapping("/api/v1/owners") // Define el path base para todos los endpoints de este controlador
@RequiredArgsConstructor // Lombok: Genera un constructor para inyectar CreateOwnerUseCase
public class OwnerController {
    // Se inyecta el Puerto de Entrada (el Caso de Uso)
    private final CreateOwnerUseCase createOwnerUseCase;

    @PostMapping // Mapea las solicitudes POST a /api/v1/owners
    public ResponseEntity<OwnerResponse> createOwner(@Valid @RequestBody CreateOwnerRequest request) {
        CreateOwnerCommand command = new CreateOwnerCommand(
                request.name(),
                request.last_name(),
                request.document(),
                request.phone(),
                request.birthdate(),
                request.email(),
                request.password()
        );

        // Invocar el caso de uso de negocio del dominio
        OwnerResponse response = createOwnerUseCase.createOwner(command);

        // Devolver una respuesta HTTP adecuada con el DTO de respuesta
        return new ResponseEntity<>(response, HttpStatus.CREATED); // Código 201 Created
    }
}
