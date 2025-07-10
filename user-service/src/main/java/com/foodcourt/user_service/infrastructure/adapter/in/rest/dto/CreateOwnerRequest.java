package com.foodcourt.user_service.infrastructure.adapter.in.rest.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record CreateOwnerRequest (
        @NotBlank(message = "Name cannot be empty") // No nulo y no vacío
        String name,
        @NotBlank(message = "Last name cannot be empty")
        String last_name,
        @NotBlank(message = "Document ID cannot be empty")
        @Size(max = 10, message = "Document ID must not exceed 10 characters") // Máximo 10 caracteres
        @Pattern(regexp = "\\d+", message = "Document ID must be numeric") // Solo dígitos
        String document,
        @NotBlank(message = "Phone number cannot be empty")
        @Size(max = 13, message = "Phone number must not exceed 13 characters") // Máximo 13 caracteres
        @Pattern(regexp = "^\\+\\d+$", message = "Phone number must start with + and contain only digits") // Formato +numeros
        String phone,
        @NotNull(message = "Date of birth cannot be empty") // No nulo
        @Past(message = "Date of birth must be in the past") // La fecha debe ser anterior a la actual
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate birthdate,
        @NotBlank(message = "Email cannot be empty")
        @Email(message = "Invalid email format") // Formato de email válido
        String email,
        @NotBlank(message = "Password cannot be empty")
        String password
) {}
