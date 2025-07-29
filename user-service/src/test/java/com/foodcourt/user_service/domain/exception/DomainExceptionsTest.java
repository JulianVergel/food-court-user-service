package com.foodcourt.user_service.domain.exception;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class DomainExceptionsTest {

    @Test
    void testUserAlreadyExistsException() {
        String expectedMessage = "El usuario ya existe";

        UserAlreadyExistsException exception = new UserAlreadyExistsException(expectedMessage);

        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void testInvalidDomainDataException() {
        String expectedMessage = "Dato inválido";

        InvalidDomainDataException exception = new InvalidDomainDataException(expectedMessage);

        assertEquals(expectedMessage, exception.getMessage());
    }
}
