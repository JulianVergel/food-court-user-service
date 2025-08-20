package com.foodcourt.user_service.domain.utils.validators;

import com.foodcourt.user_service.domain.exception.InvalidDomainDataException;
import com.foodcourt.user_service.domain.exception.UserIsNotOfLegalAgeException;
import com.foodcourt.user_service.domain.model.User;
import com.foodcourt.user_service.domain.utils.constants.DomainConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UserValidatorTest {

    private User validUser;

    @BeforeEach
    void setUp() {
        validUser = new User();
        validUser.setName("John");
        validUser.setLastName("Doe");
        validUser.setDocument("1234567890");
        validUser.setPhone("+573001234567");
        validUser.setBirthdate(LocalDate.of(2000, 1, 1));
        validUser.setEmail("john.doe@example.com");
        validUser.setPassword("password123");
    }

    @Test
    void shouldNotThrowExceptionForValidUser() {
        assertDoesNotThrow(() -> UserValidator.validateUser(validUser));
    }

    @Test
    void shouldThrowExceptionWhenNameIsBlank() {
        validUser.setName(" ");

        InvalidDomainDataException exception = assertThrows(InvalidDomainDataException.class, () -> {
            UserValidator.validateUser(validUser);
        });
        assertEquals(DomainConstants.FIELD_NAME_REQUIRED_MESSAGE, exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenLastNameIsBlank() {
        validUser.setLastName("");

        InvalidDomainDataException exception = assertThrows(InvalidDomainDataException.class, () -> {
            UserValidator.validateUser(validUser);
        });
        assertEquals(DomainConstants.FIELD_LASTNAME_REQUIRED_MESSAGE, exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenDocumentIsNotNumeric() {
        validUser.setDocument("12345abc");

        InvalidDomainDataException exception = assertThrows(InvalidDomainDataException.class, () -> {
            UserValidator.validateUser(validUser);
        });
        assertEquals(DomainConstants.FIELD_DOCUMENT_NUMERIC_MESSAGE, exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenPhoneIsInvalid() {
        validUser.setPhone("123");

        InvalidDomainDataException exception = assertThrows(InvalidDomainDataException.class, () -> {
            UserValidator.validateUser(validUser);
        });
        assertEquals(DomainConstants.FIELD_PHONE_FORMAT_MESSAGE, exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenEmailIsInvalid() {
        validUser.setEmail("invalid-email");

        InvalidDomainDataException exception = assertThrows(InvalidDomainDataException.class, () -> {
            UserValidator.validateUser(validUser);
        });
        assertEquals(DomainConstants.FIELD_EMAIL_FORMAT_MESSAGE, exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenPasswordIsNull() {
        validUser.setPassword(null);

        InvalidDomainDataException exception = assertThrows(InvalidDomainDataException.class, () -> {
            UserValidator.validateUser(validUser);
        });
        assertEquals(DomainConstants.FIELD_PASSWORD_REQUIRED_MESSAGE, exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenUserIsUnderageInValidateUser() {
        validUser.setBirthdate(LocalDate.now().minusYears(17));

        assertThrows(UserIsNotOfLegalAgeException.class, () -> {
            UserValidator.validateUser(validUser);
        });
    }

    @Test
    void shouldThrowExceptionWhenBirthdateIsNull() {
        validUser.setBirthdate(null);

        InvalidDomainDataException exception = assertThrows(InvalidDomainDataException.class, () -> {
            UserValidator.validateUser(validUser);
        });
        assertEquals(DomainConstants.FIELD_BIRTHDATE_REQUIRED_MESSAGE, exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenDocumentIsNull() {
        // Arrange
        validUser.setDocument(null);

        // Act & Assert
        InvalidDomainDataException exception = assertThrows(InvalidDomainDataException.class, () -> UserValidator.validateUser(validUser));
        assertEquals(DomainConstants.FIELD_DOCUMENT_NUMERIC_MESSAGE, exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenPhoneIsNull() {
        // Arrange
        validUser.setPhone(null);

        // Act & Assert
        InvalidDomainDataException exception = assertThrows(InvalidDomainDataException.class, () -> UserValidator.validateUser(validUser));
        assertEquals(DomainConstants.FIELD_PHONE_FORMAT_MESSAGE, exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenEmailIsNull() {
        // Arrange
        validUser.setEmail(null);

        // Act & Assert
        InvalidDomainDataException exception = assertThrows(InvalidDomainDataException.class, () -> UserValidator.validateUser(validUser));
        assertEquals(DomainConstants.FIELD_EMAIL_FORMAT_MESSAGE, exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenPasswordIsBlank() {
        // Arrange
        validUser.setPassword("  ");

        // Act & Assert
        InvalidDomainDataException exception = assertThrows(InvalidDomainDataException.class, () -> UserValidator.validateUser(validUser));
        assertEquals(DomainConstants.FIELD_PASSWORD_REQUIRED_MESSAGE, exception.getMessage());
    }
}