package com.foodcourt.user_service.infrastructure.adapter.out.security;

import com.foodcourt.user_service.domain.port.out.PasswordEncoderPort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component // Marca esta clase como un componente de Spring
public class BcryptPasswordEncoderAdapter implements PasswordEncoderPort {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public BcryptPasswordEncoderAdapter() {
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public String encode(String rawPassword) {
        return bCryptPasswordEncoder.encode(rawPassword);
    }

    @Override
    public boolean matches(String rawPassword, String encodedPassword) {
        return bCryptPasswordEncoder.matches(rawPassword, encodedPassword);
    }
}
