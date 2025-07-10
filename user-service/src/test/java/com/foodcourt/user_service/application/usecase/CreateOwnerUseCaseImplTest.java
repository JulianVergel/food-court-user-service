package com.foodcourt.user_service.application.usecase;

import com.foodcourt.user_service.application.command.CreateOwnerCommand;
import com.foodcourt.user_service.application.response.OwnerResponse;
import com.foodcourt.user_service.domain.model.Role;
import com.foodcourt.user_service.domain.model.User;
import com.foodcourt.user_service.domain.port.out.PasswordEncoderPort;
import com.foodcourt.user_service.domain.port.out.UserRepositoryPort;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) // Habilita la integración de Mockito con JUnit 5
@DisplayName("CreateOwnerUseCaseImpl Tests")
class CreateOwnerUseCaseImplTest {
    @Mock // Mockea la interfaz UserRepositoryPort
    private UserRepositoryPort userRepositoryPort;

    @Mock // Mockea la interfaz PasswordEncoderPort
    private PasswordEncoderPort passwordEncoderPort;

    @InjectMocks // Inyecta los mocks anteriores en esta instancia de CreateOwnerUseCaseImpl
    private CreateOwnerUseCaseImpl createOwnerUseCase;

    // Se ejecuta antes de cada test
    @BeforeEach
    void setUp() {
        // Aquí puedes reiniciar el estado de los mocks si es necesario
        // o configurar comportamientos comunes.
    }

    @Test
    @DisplayName("Debe crear un propietario exitosamente con datos válidos")
    void shouldCreateOwnerSuccessfullyWithValidData() {
        // Given (Dado)
        CreateOwnerCommand command = new CreateOwnerCommand(
                "Juan", "Perez", "1234567890", "+573001112233",
                LocalDate.of(1990, 5, 10), "juan.perez@example.com", "pass123"
        );

        // Comportamiento esperado de los mocks:
        // 1. userRepositoryPort: No existe email ni documentId
        when(userRepositoryPort.existsByEmail(command.email())).thenReturn(false);
        when(userRepositoryPort.existsByDocument(command.document())).thenReturn(false);

        // 2. passwordEncoderPort: Simula la encriptación
        String encodedPassword = "hashedPassword123";
        when(passwordEncoderPort.encode(command.password())).thenReturn(encodedPassword);

        // 3. userRepositoryPort: Simula el guardado y devuelve el usuario con un ID
        // Creamos un usuario de dominio simulado que sería el resultado del guardado
        User savedUser = User.createOwner(
                command.name(), command.last_name(), command.document(),
                command.phone(), command.birthdate(), command.email(), encodedPassword
        );
        savedUser.assignId("1"); // Asignamos un ID simulado

        when(userRepositoryPort.save(any(User.class))).thenReturn(savedUser); // any(User.class) para aceptar cualquier instancia de User

        // When (Cuando)
        OwnerResponse response = createOwnerUseCase.createOwner(command);

        // Then (Entonces)
        assertNotNull(response);
        assertEquals("1", response.id());
        assertEquals(command.email(), response.email());
        assertEquals(Role.PROPIETARIO, response.role());

        // Verifica que los mocks fueron llamados con los argumentos esperados
        verify(userRepositoryPort).existsByEmail(command.email()); // Verifica que se llamó este método
        verify(userRepositoryPort).existsByDocument(command.document()); // Verifica que se llamó este método
        verify(passwordEncoderPort).encode(command.password()); // Verifica que se encriptó la contraseña
        verify(userRepositoryPort).save(any(User.class)); // Verifica que se intentó guardar un usuario

        // Opcional: verifica que no se interactuaron con otros métodos de los mocks
        verifyNoMoreInteractions(userRepositoryPort, passwordEncoderPort);
    }

    @Test
    @DisplayName("Debe lanzar ValidationException si el email ya existe")
    void shouldThrowExceptionIfEmailAlreadyExists() {
        // Given
        CreateOwnerCommand command = new CreateOwnerCommand(
                "Juan", "Perez", "1234567890", "+573001112233",
                LocalDate.of(1990, 5, 10), "existing@example.com", "pass123"
        );

        // Comportamiento del mock: El email ya existe
        when(userRepositoryPort.existsByEmail(command.email())).thenReturn(true);

        // When & Then
        ValidationException thrown = assertThrows(ValidationException.class, () -> {
            createOwnerUseCase.createOwner(command);
        });

        assertEquals("Email '" + command.email() + "' already exists.", thrown.getMessage());

        // Verifica que solo se llamó existsByEmail
        verify(userRepositoryPort).existsByEmail(command.email());
        verifyNoMoreInteractions(userRepositoryPort); // Asegura que no se llamaron otros métodos del repo
        verifyNoInteractions(passwordEncoderPort); // Asegura que no se interactuó con otros mocks
    }

    @Test
    @DisplayName("Debe lanzar ValidationException si el documento de identidad ya existe")
    void shouldThrowExceptionIfDocumentIdAlreadyExists() {
        // Given
        CreateOwnerCommand command = new CreateOwnerCommand(
                "Ana", "Gomez", "9876543210", "+573004445566",
                LocalDate.of(1992, 8, 20), "ana.gomez@example.com", "pass456"
        );

        // Comportamiento del mock: Email no existe, pero DocumentId sí
        when(userRepositoryPort.existsByEmail(command.email())).thenReturn(false);
        when(userRepositoryPort.existsByDocument(command.document())).thenReturn(true);

        // When & Then
        ValidationException thrown = assertThrows(ValidationException.class, () -> {
            createOwnerUseCase.createOwner(command);
        });

        assertEquals("Document ID '" + command.document() + "' already exists.", thrown.getMessage());

        // Verifica las interacciones
        verify(userRepositoryPort).existsByEmail(command.email());
        verify(userRepositoryPort).existsByDocument(command.document());
        verifyNoMoreInteractions(userRepositoryPort);
        verifyNoInteractions(passwordEncoderPort);
    }

    @Test
    @DisplayName("Debe lanzar ValidationException si el usuario es menor de edad (validación del dominio)")
    void shouldThrowExceptionIfUserIsMinor() {
        // Given
        CreateOwnerCommand command = new CreateOwnerCommand(
                "Niño", "Pequeño", "1111111111", "+573009998877",
                LocalDate.now().minusYears(10), "nino@example.com", "pass789" // Menor de edad
        );

        // Comportamiento del mock: No existen ni email ni documentId (para llegar a la creación del User)
        when(userRepositoryPort.existsByEmail(command.email())).thenReturn(false);
        when(userRepositoryPort.existsByDocument(command.document())).thenReturn(false);
        when(passwordEncoderPort.encode(command.password())).thenReturn("anyHashedPassword");

        // When & Then
        ValidationException thrown = assertThrows(ValidationException.class, () -> {
            createOwnerUseCase.createOwner(command);
        });

        assertEquals("User must be of legal age (18+).", thrown.getMessage());

        // Verifica que se intentó la encriptación, pero no el guardado ni el envío de email
        verify(userRepositoryPort).existsByEmail(command.email());
        verify(userRepositoryPort).existsByDocument(command.document());
        verify(passwordEncoderPort).encode(command.password());
        verify(userRepositoryPort, never()).save(any(User.class)); // Asegura que save NO fue llamado
    }
}
