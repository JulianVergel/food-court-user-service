package com.foodcourt.user_service.application.dto.request;

import jakarta.validation.constraints.*;
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
    @Size(min = 10, max = 10, message = "El documento de identidad debe tener 10 caracteres")
    @NotBlank(message = "El documento es obligatorio")
    private String document;

    @Pattern(regexp = "^\\+?[0-9]{1,12}$", message = "El celular debe ser numérico y puede tener un prefijo con '+'")
    @Size(min = 10, max = 13, message = "El celular debe tener entre 10 y 13 caracteres")
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
