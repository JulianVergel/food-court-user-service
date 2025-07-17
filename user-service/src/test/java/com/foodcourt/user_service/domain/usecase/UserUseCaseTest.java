package com.foodcourt.user_service.domain.usecase;

import com.foodcourt.user_service.domain.exception.UserAlreadyExistsException;
import com.foodcourt.user_service.domain.exception.UserIsNotOfLegalAgeException;
import com.foodcourt.user_service.domain.model.Role;
import com.foodcourt.user_service.domain.model.User;
import com.foodcourt.user_service.domain.spi.IPasswordEncoderPort;
import com.foodcourt.user_service.domain.spi.IRolePersistencePort;
import com.foodcourt.user_service.domain.spi.IUserPersistencePort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

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

    @Test
    void mustCreateOwnerAndSaveUser() {
        User user = new User(1L, "Test", "User", "1234567", "+573001234567",
                LocalDate.of(2000, 1, 1), "test@example.com", "password123", null);
        Role ownerRole = new Role(2L, "Propietario", "Rol de propietario");

        when(rolePersistencePort.findRoleByName("Propietario")).thenReturn(ownerRole);
        when(passwordEncoderPort.encodePassword("password123")).thenReturn("encodedPassword");

        when(userPersistencePort.existsByDocument(user.getDocument())).thenReturn(false);
        when(userPersistencePort.existsByEmail(user.getEmail())).thenReturn(false);
        when(userPersistencePort.existsByPhone(user.getPhone())).thenReturn(false);

        userUseCase.createOwner(user);

        verify(userPersistencePort, times(1)).saveUser(user);
        assert user.getPassword().equals("encodedPassword");
        assert user.getRole().equals(ownerRole);
    }

    @Test
    void shouldThrowExceptionWhenUserIsUnderage() {
        User underageUser = new User(1L, "Minor", "User", "111222333", "+57300111222",
                LocalDate.now().minusYears(17), "minor@example.com", "password123", null);

        assertThrows(UserIsNotOfLegalAgeException.class, () -> {
            userUseCase.createOwner(underageUser);
        });

        verify(userPersistencePort, never()).saveUser(any(User.class));
    }

    @Test
    void shouldThrowExceptionWhenDocumentAlreadyExists() {
        User user = new User(1L, "Test", "User", "1234567", "+573001234567",
                LocalDate.of(2000, 1, 1), "test@example.com", "password123", null);

        when(userPersistencePort.existsByDocument("1234567")).thenReturn(true);

        assertThrows(UserAlreadyExistsException.class, () -> {
            userUseCase.createOwner(user);
        });

        verify(userPersistencePort, never()).saveUser(any(User.class));
    }

    @Test
    void shouldThrowExceptionWhenEmailAlreadyExists() {
        User user = new User(1L, "Test", "User", "1234567", "+573001234567",
                LocalDate.of(2000, 1, 1), "test@example.com", "password123", null);

        when(userPersistencePort.existsByEmail("test@example.com")).thenReturn(true);

        assertThrows(UserAlreadyExistsException.class, () -> {
            userUseCase.createOwner(user);
        });

        verify(userPersistencePort, never()).saveUser(any(User.class));
    }

    @Test
    void shouldThrowExceptionWhenPhoneAlreadyExists() {
        User user = new User(1L, "Test", "User", "1234567", "+573001234567",
                LocalDate.of(2000, 1, 1), "test@example.com", "password123", null);

        when(userPersistencePort.existsByPhone("+573001234567")).thenReturn(true);

        assertThrows(UserAlreadyExistsException.class, () -> {
            userUseCase.createOwner(user);
        });

        verify(userPersistencePort, never()).saveUser(any(User.class));
    }
}
