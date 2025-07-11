package com.foodcourt.user_service.infrastructure.input.rest.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UserRequestDto {
    @NotBlank(message = "El nombre es obligatorio")
    private String name;

    @NotBlank(message = "El apellido es obligatorio")
    private String lastName;

    @Pattern(regexp = "^[0-9]+$", message = "El documento de identidad debe ser numérico")
    @NotBlank(message = "El documento es obligatorio")
    private String document;

    @Pattern(regexp = "^\\+?[0-9]{1,12}$", message = "El celular debe contener máximo 13 caracteres y puede iniciar con '+'")
    @NotBlank(message = "El celular es obligatorio")
    private String phone;

    @NotNull(message = "La fecha de nacimiento es obligatoria")
    private LocalDate birthdate;

    @Email(message = "El correo debe tener un formato válido")
    @NotBlank(message = "El correo es obligatorio")
    private String email;

    @NotBlank(message = "La clave es obligatoria")
    private String password;
}
