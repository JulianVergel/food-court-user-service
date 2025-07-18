package com.foodcourt.user_service.domain.usecase;

import com.foodcourt.user_service.domain.exception.UserAlreadyExistsException;
import com.foodcourt.user_service.domain.exception.UserIsNotOfLegalAgeException;
import com.foodcourt.user_service.domain.model.Role;
import com.foodcourt.user_service.domain.model.User;
import com.foodcourt.user_service.domain.spi.IPasswordEncoderPort;
import com.foodcourt.user_service.domain.spi.IRolePersistencePort;
import com.foodcourt.user_service.domain.spi.IUserPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserUseCaseTest {
    @Mock
    private IUserPersistencePort userPersistencePort;
    @Mock
    private IPasswordEncoderPort passwordEncoderPort;
    @Mock
    private IRolePersistencePort rolePersistencePort;

    @InjectMocks
    UserUseCase userUseCase;

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
        user.setFailedLoginAttempts(0);
        user.setLockTime(null);
    }

    @Test
    void mustCreateOwnerAndSaveUser() {
        // Arrange
        Role ownerRole = new Role(2L, "Propietario", "Rol de propietario");
        when(rolePersistencePort.findRoleByName("Propietario")).thenReturn(ownerRole);
        when(passwordEncoderPort.encodePassword(user.getPassword())).thenReturn("encodedPassword");

        when(userPersistencePort.existsByDocument(user.getDocument())).thenReturn(false);
        when(userPersistencePort.existsByEmail(user.getEmail())).thenReturn(false);
        when(userPersistencePort.existsByPhone(user.getPhone())).thenReturn(false);

        // Act
        userUseCase.createOwner(user);

        // Assert
        verify(userPersistencePort).saveUser(user);
        assertEquals("encodedPassword", user.getPassword());
        assertEquals(ownerRole, user.getRole());
    }

    @Test
    void shouldThrowExceptionWhenDocumentAlreadyExists() {
        // Arrange
        when(userPersistencePort.existsByDocument(user.getDocument())).thenReturn(true);

        // Act & Assert
        assertThrows(UserAlreadyExistsException.class, () -> userUseCase.createOwner(user));
        verify(userPersistencePort, never()).saveUser(any(User.class));
    }

    @Test
    void shouldThrowExceptionWhenEmailAlreadyExists() {
        // Arrange
        when(userPersistencePort.existsByEmail(user.getEmail())).thenReturn(true);

        // Act & Assert
        assertThrows(UserAlreadyExistsException.class, () -> userUseCase.createOwner(user));
        verify(userPersistencePort, never()).saveUser(any(User.class));
    }

    @Test
    void shouldThrowExceptionWhenPhoneAlreadyExists() {
        // Arrange
        when(userPersistencePort.existsByPhone(user.getPhone())).thenReturn(true);

        // Act & Assert
        assertThrows(UserAlreadyExistsException.class, () -> userUseCase.createOwner(user));
        verify(userPersistencePort, never()).saveUser(any(User.class));
    }

    @Test
    void shouldCreateEmployeeAndSaveUserSuccessfully() {
        User employeeUser = new User();
        employeeUser.setName("Empleado");
        employeeUser.setLastName("Prueba");
        employeeUser.setEmail("employee@example.com");
        employeeUser.setDocument("9876543210");
        employeeUser.setPassword("password123");
        employeeUser.setBirthdate(LocalDate.of(2000, 1, 1));
        employeeUser.setPhone("+573004445566");

        Role employeeRole = new Role(3L, "Empleado", "Rol de empleado");

        when(userPersistencePort.existsByDocument(employeeUser.getDocument())).thenReturn(false);
        when(userPersistencePort.existsByEmail(employeeUser.getEmail())).thenReturn(false);
        when(rolePersistencePort.findRoleByName("Empleado")).thenReturn(employeeRole);
        when(passwordEncoderPort.encodePassword(anyString())).thenReturn("encodedPassword");

        userUseCase.createEmployee(employeeUser);

        verify(userPersistencePort).saveUser(employeeUser);

        assertEquals(employeeRole, employeeUser.getRole());
    }
}
