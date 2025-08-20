package com.foodcourt.user_service.domain.model;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.assertEquals;

class UserTest {

    @Test
    void testUserGettersAndSetters() {
        Role role = new Role(1L, "Test Role", "Description");
        LocalDate birthdate = LocalDate.of(1995, 10, 20);

        User user = new User(1L, "John", "Doe", "12345", "+57",
                birthdate, "john.doe@mail.com", "pass123", role, 1L);

        assertEquals(1L, user.getId());
        assertEquals("John", user.getName());
        assertEquals("Doe", user.getLastName());
        assertEquals("12345", user.getDocument());
        assertEquals("+57", user.getPhone());
        assertEquals(birthdate, user.getBirthdate());
        assertEquals("john.doe@mail.com", user.getEmail());
        assertEquals("pass123", user.getPassword());
        assertEquals(role, user.getRole());
        assertEquals(1L, user.getRestaurantId());

        User user2 = new User();
        user2.setName("Jane");

        assertEquals("Jane", user2.getName());
    }
}