package com.foodcourt.user_service.domain.usecase;

import com.foodcourt.user_service.domain.exception.UserAlreadyExistsException;
import com.foodcourt.user_service.domain.model.Role;
import com.foodcourt.user_service.domain.model.User;
import com.foodcourt.user_service.domain.spi.IPasswordEncoderPort;
import com.foodcourt.user_service.domain.spi.IRolePersistencePort;
import com.foodcourt.user_service.domain.spi.IUserPersistencePort;
import com.foodcourt.user_service.domain.utils.validators.UserValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserUseCaseTest {
    @Mock
    private IUserPersistencePort userPersistencePort;
    @Mock
    private IPasswordEncoderPort passwordEncoderPort;
    @Mock
    private IRolePersistencePort rolePersistencePort;
    @InjectMocks
    private UserUseCase userUseCase;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setName("Test");
        user.setLastName("User");
        user.setDocument("1234567890");
        user.setPhone("+573001234567");
        user.setBirthdate(LocalDate.of(2000, 1, 1));
        user.setEmail("test@example.com");
        user.setPassword("password123");
        user.setRole(null);
    }

    @Test
    void mustCreateOwnerAndSaveUser() {
        try (MockedStatic<UserValidator> mockedValidator = mockStatic(UserValidator.class)) {
            Role ownerRole = new Role(2L, "Propietario", "Rol de propietario");
            when(rolePersistencePort.findRoleByName("Propietario")).thenReturn(ownerRole);
            when(passwordEncoderPort.encodePassword(user.getPassword())).thenReturn("encodedPassword");
            when(userPersistencePort.existsByDocument(user.getDocument())).thenReturn(false);
            when(userPersistencePort.existsByEmail(user.getEmail())).thenReturn(false);

            userUseCase.createOwner(user);

            mockedValidator.verify(() -> UserValidator.validateUser(user)); // Verificamos que el validador fue llamado
            verify(userPersistencePort).saveUser(user);
            assertEquals("encodedPassword", user.getPassword());
            assertEquals(ownerRole, user.getRole());
        }
    }

    @Test
    void shouldThrowExceptionWhenDocumentAlreadyExists() {
        try (MockedStatic<UserValidator> mockedValidator = mockStatic(UserValidator.class)) {
            when(userPersistencePort.existsByDocument(user.getDocument())).thenReturn(true);

            assertThrows(UserAlreadyExistsException.class, () -> userUseCase.createOwner(user));
            mockedValidator.verify(() -> UserValidator.validateUser(user));
            verify(userPersistencePort, never()).saveUser(any(User.class));
        }
    }

    @Test
    void shouldThrowExceptionWhenEmailAlreadyExists() {
        try (MockedStatic<UserValidator> mockedValidator = mockStatic(UserValidator.class)) {
            when(userPersistencePort.existsByEmail(user.getEmail())).thenReturn(true);

            assertThrows(UserAlreadyExistsException.class, () -> userUseCase.createOwner(user));
            mockedValidator.verify(() -> UserValidator.validateUser(user));
            verify(userPersistencePort, never()).saveUser(any(User.class));
        }
    }

    @Test
    void shouldCreateEmployeeAndSaveUserSuccessfully() {
        try (MockedStatic<UserValidator> mockedValidator = mockStatic(UserValidator.class)) {
            Role employeeRole = new Role(3L, "Empleado", "Rol de empleado");
            when(userPersistencePort.existsByDocument(user.getDocument())).thenReturn(false);
            when(userPersistencePort.existsByEmail(user.getEmail())).thenReturn(false);
            when(rolePersistencePort.findRoleByName("Empleado")).thenReturn(employeeRole);
            when(passwordEncoderPort.encodePassword(anyString())).thenReturn("encodedPassword");

            userUseCase.createEmployee(user);

            mockedValidator.verify(() -> UserValidator.validateUser(user));
            verify(userPersistencePort).saveUser(user);
            assertEquals(employeeRole, user.getRole());
        }
    }

    @Test
    void shouldCreateClientAndSaveUserSuccessfully() {
        try (MockedStatic<UserValidator> mockedValidator = mockStatic(UserValidator.class)) {
            Role clientRole = new Role(4L, "Cliente", "Rol de cliente");
            when(userPersistencePort.existsByDocument(user.getDocument())).thenReturn(false);
            when(userPersistencePort.existsByEmail(user.getEmail())).thenReturn(false);
            when(rolePersistencePort.findRoleByName("Cliente")).thenReturn(clientRole);
            when(passwordEncoderPort.encodePassword(anyString())).thenReturn("encodedPassword");

            userUseCase.createClient(user);

            mockedValidator.verify(() -> UserValidator.validateUser(user));
            verify(userPersistencePort).saveUser(user);
            assertEquals(clientRole, user.getRole());
        }
    }

    @Test
    void shouldThrowExceptionWhenPhoneAlreadyExists() {
        try (MockedStatic<UserValidator> mockedValidator = mockStatic(UserValidator.class)) {
            when(userPersistencePort.existsByPhone(user.getPhone())).thenReturn(true);

            assertThrows(UserAlreadyExistsException.class, () -> userUseCase.createOwner(user));

            mockedValidator.verify(() -> UserValidator.validateUser(user));
            verify(userPersistencePort, never()).saveUser(any(User.class));
        }
    }

    @Test
    void shouldReturnUserWhenFoundById() {
        Long userId = 1L;
        when(userPersistencePort.findById(userId)).thenReturn(user);

        User foundUser = userUseCase.getUserById(userId);

        verify(userPersistencePort).findById(userId);
        assertEquals(user, foundUser);
    }
}