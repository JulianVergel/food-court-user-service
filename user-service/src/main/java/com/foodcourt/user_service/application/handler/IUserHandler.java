package com.foodcourt.user_service.application.handler;

import com.foodcourt.user_service.application.dto.request.UserRequestDto;
import com.foodcourt.user_service.application.dto.response.UserResponseDto;

public interface IUserHandler {
    void createOwner(UserRequestDto userRequestDto);
    UserResponseDto getUserById(Long id);
    void createEmployee(UserRequestDto userRequestDto);
}
