package com.foodcourt.user_service.infrastructure.input.rest.controller;

import com.foodcourt.user_service.infrastructure.input.rest.dto.request.LoginRequestDto;
import com.foodcourt.user_service.infrastructure.input.rest.dto.response.LoginResponseDto;
import com.foodcourt.user_service.infrastructure.output.security.jwt.JwtProvider;
import com.foodcourt.user_service.infrastructure.utils.InfrastructureConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    @Operation(summary = InfrastructureConstants.SUMMARY_LOGIN)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = InfrastructureConstants.RESPONSE_CODE_200),
            @ApiResponse(responseCode = "400", description = InfrastructureConstants.RESPONSE_CODE_400),
    })
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDto.getEmail(), loginRequestDto.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateToken(authentication);
        return ResponseEntity.ok(new LoginResponseDto(jwt));
    }
}
