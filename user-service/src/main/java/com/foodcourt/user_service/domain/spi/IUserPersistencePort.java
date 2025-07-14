package com.foodcourt.user_service.domain.spi;

import com.foodcourt.user_service.domain.model.User;

public interface IUserPersistencePort {
    User saveUser(User user);

    boolean existsByDocument(String document);
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);
}
