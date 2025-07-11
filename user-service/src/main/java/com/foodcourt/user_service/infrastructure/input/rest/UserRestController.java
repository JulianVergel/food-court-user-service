package com.foodcourt.user_service.infrastructure.input.rest;

import com.foodcourt.user_service.domain.api.IUserServicePort;
import com.foodcourt.user_service.infrastructure.input.rest.dto.request.UserRequestDto;
import com.foodcourt.user_service.infrastructure.input.rest.mapper.IUserRequestMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/usuario")
@RequiredArgsConstructor
public class UserRestController {
    private final IUserServicePort userServicePort;
    private final IUserRequestMapper userRequestMapper;

    @PostMapping("/propietario")
    public ResponseEntity<Void> createOwner(@Valid @RequestBody UserRequestDto userRequestDto) {
        var user = userRequestMapper.toUser(userRequestDto);
        userServicePort.createOwner(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
