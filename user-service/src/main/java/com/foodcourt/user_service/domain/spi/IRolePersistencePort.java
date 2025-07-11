package com.foodcourt.user_service.domain.spi;

import com.foodcourt.user_service.domain.model.Role;

public interface IRolePersistencePort {
    Role findRoleByName(String name);
}
