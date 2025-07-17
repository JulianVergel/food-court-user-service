package com.foodcourt.user_service.application.dto.response;

import com.foodcourt.user_service.domain.model.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseDto {
    private Long id;
    private String name;
    private String lastName;
    private String document;
    private String phone;
    private Role role;
}
