package com.foodcourt.user_service.infrastructure.input.rest.dto.request;

import lombok.Getter;

@Getter
public class LoginRequestDto {
    private String email;
    private String password;
}
