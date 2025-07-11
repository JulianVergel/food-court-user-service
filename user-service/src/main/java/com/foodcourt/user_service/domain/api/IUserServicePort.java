package com.foodcourt.user_service.domain.api;

import com.foodcourt.user_service.domain.model.User;

public interface IUserServicePort {
    void createOwner(User user);
}
