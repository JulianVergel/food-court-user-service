package com.foodcourt.user_service.application.handler.impl;

import com.foodcourt.user_service.application.dto.request.UserRequestDto;
import com.foodcourt.user_service.application.dto.response.UserResponseDto;
import com.foodcourt.user_service.application.handler.IUserHandler;
import com.foodcourt.user_service.application.mapper.request.IUserRequestMapper;
import com.foodcourt.user_service.application.mapper.response.IUserResponseMapper;
import com.foodcourt.user_service.domain.api.IUserServicePort;
import com.foodcourt.user_service.domain.model.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class UserHandler implements IUserHandler {

    private final IUserServicePort userServicePort;
    private final IUserRequestMapper userRequestMapper;
    private final IUserResponseMapper userResponseMapper;

    @Override
    public void createOwner(UserRequestDto userRequestDto) {
        User user = userRequestMapper.toUser(userRequestDto);
        userServicePort.createOwner(user);
    }

    @Override
    public UserResponseDto getUserById(Long id) {
        User user = userServicePort.getUserById(id);
        return userResponseMapper.toUserResponseDto(user);
    }
}
