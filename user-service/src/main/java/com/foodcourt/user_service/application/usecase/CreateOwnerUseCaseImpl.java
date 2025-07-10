package com.foodcourt.user_service.application.usecase;

import com.foodcourt.user_service.application.command.CreateOwnerCommand;
import com.foodcourt.user_service.application.response.OwnerResponse;
import com.foodcourt.user_service.domain.model.User;
import com.foodcourt.user_service.domain.port.in.CreateOwnerUseCase;
import com.foodcourt.user_service.domain.port.out.PasswordEncoderPort;
import com.foodcourt.user_service.domain.port.out.UserRepositoryPort;
import jakarta.transaction.Transactional;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service // Componente de servicio de Spring, manejado por el contenedor de Spring
@RequiredArgsConstructor // Lombok: genera un constructor con argumentos para todos los campos 'final'
public class CreateOwnerUseCaseImpl implements CreateOwnerUseCase {
    // Inyección de los puertos de salida
    private final UserRepositoryPort userRepositoryPort;
    private final PasswordEncoderPort passwordEncoderPort;

    @Transactional // Anotación de Spring para gestionar transacciones de base de datos
    @Override
    public OwnerResponse createOwner(CreateOwnerCommand command) {
        // 1. Validaciones de unicidad
        if (userRepositoryPort.existsByEmail(command.email())) {
            throw new ValidationException("Email '" + command.email() + "' already exists.");
        }
        if (userRepositoryPort.existsByDocument(command.document())) {
            throw new ValidationException("Document ID '" + command.document() + "' already exists.");
        }

        // Encriptar la contraseña utilizando el puerto de PasswordEncoder
        String encodedPassword = passwordEncoderPort.encode(command.password());

        User ownerUser = User.createOwner(
                command.name(),
                command.last_name(),
                command.document(),
                command.phone(),
                command.birthdate(),
                command.email(),
                encodedPassword // Pasa la contraseña ya encriptada a la entidad de dominio
        );

        // Persistir el usuario utilizando el puerto de UserRepository
        User savedUser = userRepositoryPort.save(ownerUser);

        // Mapear la entidad de dominio guardada a un DTO de respuesta
        return new OwnerResponse(
                savedUser.getId(),
                savedUser.getName(),
                savedUser.getLast_name(),
                savedUser.getDocument(),
                savedUser.getPhone(),
                savedUser.getBirthdate(),
                savedUser.getEmail(),
                savedUser.getRole()
        );
    }
}
