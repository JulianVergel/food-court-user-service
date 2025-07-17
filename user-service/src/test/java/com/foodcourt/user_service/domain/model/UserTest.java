package com.foodcourt.user_service.domain.model;

import com.foodcourt.user_service.domain.exception.UserIsNotOfLegalAgeException;
import com.foodcourt.user_service.domain.utils.validators.UserValidator;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UserTest {
    @Test
    void shouldNotThrowExceptionWhenUserIsOfLegalAge() {
        LocalDate legalAgeBirthdate = LocalDate.now().minusYears(18);

        // Verificamos que NO se lanza ninguna excepción
        assertDoesNotThrow(() -> UserValidator.validateLegalAge(legalAgeBirthdate));
    }

    @Test
    void shouldThrowExceptionWhenUserIsUnderage() {
        LocalDate underageBirthdate = LocalDate.now().minusYears(17);

        // Verificamos que SI se lanza la excepción correcta
        assertThrows(UserIsNotOfLegalAgeException.class, () -> {
            UserValidator.validateLegalAge(underageBirthdate);
        });
    }
}
