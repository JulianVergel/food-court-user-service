package com.foodcourt.user_service.infrastructure.input.rest.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResponseDto {
    private String jwt;
}
