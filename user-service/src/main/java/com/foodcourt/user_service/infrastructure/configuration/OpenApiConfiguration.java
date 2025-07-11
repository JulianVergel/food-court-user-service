package com.foodcourt.user_service.infrastructure.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Food Court - User Service API",
                version = "1.0.0",
                description = "API para gestionar los usuarios del sistema Food Court. Permite la creación de propietarios y la gestión de roles."
        )
)
public class OpenApiConfiguration {
}
