package com.foodcourt.user_service.application.response;

import com.foodcourt.user_service.domain.model.Role;

import java.time.LocalDate;

public record OwnerResponse (
        String id,
        String name,
        String last_name,
        String document,
        String phone,
        LocalDate birthdate,
        String email,
        Role role
) {}
