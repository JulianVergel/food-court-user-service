package com.foodcourt.user_service.domain.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class RoleTest {

    @Test
    void testRoleGettersAndSetters() {
        Role role = new Role(1L, "Admin", "Administrador");

        assertEquals(1L, role.getId());
        assertEquals("Admin", role.getName());
        assertEquals("Administrador", role.getDescription());

        role.setId(2L);
        role.setName("Owner");
        role.setDescription("Propietario");

        assertEquals(2L, role.getId());
        assertEquals("Owner", role.getName());
        assertEquals("Propietario", role.getDescription());
    }
}
