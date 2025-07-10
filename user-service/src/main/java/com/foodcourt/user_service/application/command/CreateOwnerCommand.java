package com.foodcourt.user_service.application.command;

import java.time.LocalDate;

public record CreateOwnerCommand (
        String name,
        String last_name,
        String document,
        String phone,
        LocalDate birthdate,
        String email,
        String password // La contraseña en texto plano en este punto (antes de encriptar)
) {}
