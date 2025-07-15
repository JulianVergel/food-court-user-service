package com.foodcourt.user_service.domain.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserTest {
    @Test
    void shouldReturnTrueWhenUserIsExactly18YearsOld() {
        // Arrange: Un usuario que cumplió 18 años hoy
        User user = new User(1L, "Test", "User", "1234567", "+573001234567",
                LocalDate.of(2000, 1, 1), "test@example.com", "password123", null);
        user.setBirthdate(LocalDate.now().minusYears(18));

        // Act & Assert: Verificamos que es mayor de edad
        assertTrue(user.isOfLegalAge(), "El usuario debería ser mayor de edad");
    }

    @Test
    void shouldReturnFalseWhenUserIsUnderage() {
        // Arrange: Un usuario que cumplirá 18 mañana
        User user = new User(1L, "Test", "User", "1234567", "+573001234567",
                null, "test@example.com", "password123", null);
        user.setBirthdate(LocalDate.now().minusYears(18).plusDays(1));

        // Act & Assert: Verificamos que aún no es mayor de edad
        assertFalse(user.isOfLegalAge(), "El usuario no debería ser mayor de edad");
    }

    @Test
    void shouldReturnTrueWhenUserIsOver18() {
        // Arrange: Un usuario que tiene más de 18
        User user = new User(1L, "Test", "User", "1234567", "+573001234567",
                LocalDate.of(2000, 1, 1), "test@example.com", "password123", null);
        user.setBirthdate(LocalDate.of(2000, 1, 1));

        // Act & Assert: Verificamos que es mayor de edad
        assertTrue(user.isOfLegalAge(), "El usuario debería ser mayor de edad");
    }
}
