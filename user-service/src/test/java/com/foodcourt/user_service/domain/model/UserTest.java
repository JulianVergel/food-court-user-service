package com.foodcourt.user_service.domain.model;

import jakarta.validation.ValidationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("User Domain Model Tests")
class UserTest {
    @Test
    @DisplayName("Debe crear un propietario válido cuando los datos son correctos y es mayor de edad")
    void shouldCreateValidOwnerWhenDataIsCorrectAndIsAdult() {
        // Given (Dado) - Datos de entrada válidos
        String name = "Test";
        String last_name = "User";
        String document = "1234567890";
        String phone = "+573001234567";
        LocalDate birthdate = LocalDate.of(1990, 1, 1); // Mayor de 18
        String email = "test.user@example.com";
        String password = "rawPassword123";

        // When (Cuando) - Se invoca el método de creación
        User owner = User.createOwner(name, last_name, document, phone, birthdate, email, password);

        // Then (Entonces) - Se verifican los resultados
        assertNotNull(owner);
        assertEquals(name, owner.getName());
        assertEquals(last_name, owner.getLast_name());
        assertEquals(document, owner.getDocument());
        assertEquals(phone, owner.getPhone());
        assertEquals(birthdate, owner.getBirthdate());
        assertEquals(email, owner.getEmail());
        assertEquals(password, owner.getPassword()); // En el dominio, la password sigue siendo raw
        assertEquals(Role.PROPIETARIO, owner.getRole()); // Asegura que el rol sea OWNER
    }

    @Test
    @DisplayName("No debe crear propietario si es menor de edad")
    void shouldNotCreateOwnerIfMinor() {
        // Given
        String name = "Minor";
        String last_name = "User";
        String document = "1234567891";
        String phone = "+573001234568";
        LocalDate birthdate= LocalDate.now().minusYears(17); // 17 años, menor de edad
        String email = "minor.user@example.com";
        String password = "rawPassword123";

        // When & Then (Cuando & Entonces) - Se espera una excepción
        ValidationException thrown = assertThrows(ValidationException.class, () -> {
            User.createOwner(name, last_name, document, phone, birthdate, email, password);
        });

        assertEquals("User must be of legal age (18+).", thrown.getMessage());
    }

    @Test
    @DisplayName("No debe crear propietario con email inválido")
    void shouldNotCreateOwnerWithInvalidEmail() {
        // Given
        String name = "InvalidEmail";
        String last_name = "User";
        String document = "1234567892";
        String phone = "+573001234569";
        LocalDate birthdate = LocalDate.of(1990, 1, 1);
        String email = "invalid-email"; // Email inválido
        String password = "rawPassword123";

        // When & Then
        ValidationException thrown = assertThrows(ValidationException.class, () -> {
            User.createOwner(name, last_name, document, phone, birthdate, email, password);
        });

        assertEquals("Invalid email format.", thrown.getMessage());
    }

    @Test
    @DisplayName("No debe crear propietario con número de teléfono inválido (longitud)")
    void shouldNotCreateOwnerWithInvalidPhoneNumberLength() {
        // Given
        String name = "InvalidPhone";
        String last_name = "User";
        String document = "1234567893";
        String phone = "+573001234567890123"; // Más de 13 caracteres
        LocalDate birthdate = LocalDate.of(1990, 1, 1);
        String email = "phone@example.com";
        String password = "rawPassword123";

        // When & Then
        ValidationException thrown = assertThrows(ValidationException.class, () -> {
            User.createOwner(name, last_name, document, phone, birthdate, email, password);
        });

        assertEquals("Invalid phone number format. Max 13 chars, starts with +, numeric.", thrown.getMessage());
    }

    @Test
    @DisplayName("No debe crear propietario si el campo de documento no es numérico")
    void shouldNotCreateOwnerIfDocumentIsNotNumeric() {
        // Given
        String name = "InvalidDoc";
        String last_name = "User";
        String document = "123abc456"; // No numérico
        String phone = "+573001234567";
        LocalDate birthdate = LocalDate.of(1990, 1, 1);
        String email = "doc@example.com";
        String password = "rawPassword123";

        // When & Then
        ValidationException thrown = assertThrows(ValidationException.class, () -> {
            User.createOwner(name, last_name, document, phone, birthdate, email, password);
        });

        assertEquals("Document ID must be numeric", thrown.getMessage());
    }
}
