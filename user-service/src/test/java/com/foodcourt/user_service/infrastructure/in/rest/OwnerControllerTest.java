package com.foodcourt.user_service.infrastructure.in.rest; // <-- Verifica este paquete, debería ser .adapter.in.rest

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import com.foodcourt.user_service.application.command.CreateOwnerCommand;
import com.foodcourt.user_service.application.response.OwnerResponse;
import com.foodcourt.user_service.domain.model.Role;
import com.foodcourt.user_service.domain.port.in.CreateOwnerUseCase;
import com.foodcourt.user_service.infrastructure.adapter.in.rest.OwnerController;
import com.foodcourt.user_service.infrastructure.adapter.in.rest.dto.CreateOwnerRequest;

import jakarta.validation.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = OwnerController.class, // Especifica el controlador a probar
        excludeAutoConfiguration = SecurityAutoConfiguration.class) // <--- AÑADE ESTA LÍNEA
@DisplayName("OwnerController Integration Tests")
class OwnerControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CreateOwnerUseCase createOwnerUseCase;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    @Test
    @DisplayName("Debe crear un propietario y devolver 201 Created")
    void shouldCreateOwnerAndReturn201Created() throws Exception {
        CreateOwnerRequest request = new CreateOwnerRequest(
                "Julian", "Lopez", "1000000001", "+573101234567",
                LocalDate.of(1990, 7, 10), "julian.lopez@example.com", "Password123"
        );

        OwnerResponse expectedResponse = new OwnerResponse(
                "1", request.name(), request.last_name(), request.document(),
                request.phone(), request.birthdate(), request.email(), Role.PROPIETARIO // Ya has corregido los nombres de campos en CreateOwnerRequest
        );
        when(createOwnerUseCase.createOwner(any(CreateOwnerCommand.class))).thenReturn(expectedResponse);

        mockMvc.perform(post("/api/v1/owners")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request).getBytes(StandardCharsets.UTF_8)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value(request.name()))
                .andExpect(jsonPath("$.last_name").value(request.last_name()))
                .andExpect(jsonPath("$.document").value(request.document()))
                .andExpect(jsonPath("$.phone").value(request.phone()))
                .andExpect(jsonPath("$.birthdate").value(request.birthdate().toString()))
                .andExpect(jsonPath("$.email").value(request.email()))
                .andExpect(jsonPath("$.role").value(Role.PROPIETARIO.toString()));
    }

    @Test
    @DisplayName("Debe devolver 400 Bad Request si la solicitud es inválida (validación de formato)")
    void shouldReturn400BadRequestForInvalidRequest() throws Exception {
        CreateOwnerRequest invalidRequest = new CreateOwnerRequest(
                "", "Perez", "123", "invalid-phone",
                LocalDate.now().plusYears(1), "invalid-email", ""
        );

        mockMvc.perform(post("/api/v1/owners")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest).getBytes(StandardCharsets.UTF_8)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Validation failed"))
                .andExpect(jsonPath("$.errors.name").value("Name cannot be empty"))
                .andExpect(jsonPath("$.errors.phone").value("Phone number must start with + and contain only digits")) // <-- Usar snake_case para phone
                .andExpect(jsonPath("$.errors.email").value("Invalid email format"))
                .andExpect(jsonPath("$.errors.birthdate").value("Date of birth must be in the past")) // <-- Usar snake_case para birthdate
                .andExpect(jsonPath("$.errors.password").value("Password cannot be empty"));
    }

    @Test
    @DisplayName("Debe devolver 400 Bad Request si la lógica de negocio falla (ej. email ya existe)")
    void shouldReturn400BadRequestIfBusinessLogicFails() throws Exception {
        CreateOwnerRequest request = new CreateOwnerRequest(
                "Julian", "Lopez", "1000000001", "+573101234567",
                LocalDate.of(1990, 7, 10), "existing@example.com", "Password123"
        );

        when(createOwnerUseCase.createOwner(any(CreateOwnerCommand.class)))
                .thenThrow(new ValidationException("Email 'existing@example.com' already exists."));

        mockMvc.perform(post("/api/v1/owners")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request).getBytes(StandardCharsets.UTF_8)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Email 'existing@example.com' already exists."))
                .andExpect(jsonPath("$.details").value("uri=/api/v1/owners"))
                .andExpect(jsonPath("$.errors").isEmpty());
    }
}