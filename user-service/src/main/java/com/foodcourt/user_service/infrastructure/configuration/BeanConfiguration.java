package com.foodcourt.user_service.infrastructure.configuration;

import com.foodcourt.user_service.domain.api.IUserServicePort;
import com.foodcourt.user_service.domain.spi.IPasswordEncoderPort;
import com.foodcourt.user_service.domain.spi.IRolePersistencePort;
import com.foodcourt.user_service.domain.spi.IUserPersistencePort;
import com.foodcourt.user_service.domain.usecase.UserUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {

    private final IUserPersistencePort userPersistencePort;
    private final IPasswordEncoderPort passwordEncoderPort;
    private final IRolePersistencePort rolePersistencePort;

    @Bean
    public IUserServicePort userServicePort() {
        return new UserUseCase(userPersistencePort, passwordEncoderPort, rolePersistencePort);
    }
}
