package com.foodcourt.user_service.application.handler;

import com.foodcourt.user_service.application.dto.request.UserRequestDto;

public interface IUserHandler {
    void createOwner(UserRequestDto userRequestDto);
}
