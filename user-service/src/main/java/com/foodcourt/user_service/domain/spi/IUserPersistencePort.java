package com.foodcourt.user_service.domain.spi;

import com.foodcourt.user_service.domain.model.User;

public interface IUserPersistencePort {
    User saveUser(User user);
}
