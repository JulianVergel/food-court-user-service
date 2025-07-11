package com.foodcourt.user_service.application.handler;

import com.foodcourt.user_service.domain.api.IUserServicePort;
import com.foodcourt.user_service.domain.exception.UserIsNotOfLegalAgeException;
import com.foodcourt.user_service.domain.model.Role;
import com.foodcourt.user_service.domain.model.User;
import com.foodcourt.user_service.domain.spi.IPasswordEncoderPort;
import com.foodcourt.user_service.domain.spi.IRolePersistencePort;
import com.foodcourt.user_service.domain.spi.IUserPersistencePort;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class UserHandler implements IUserServicePort {
    private final IUserPersistencePort userPersistencePort;
    private final IPasswordEncoderPort passwordEncoderPort;
    private final IRolePersistencePort rolePersistencePort;

    @Override
    public void createOwner(User user) {
        if (!user.isOfLegalAge()) {
            throw new UserIsNotOfLegalAgeException();
        }

        Role roleOwner = rolePersistencePort.findRoleByName("Propietario");
        user.setRole(roleOwner);

        String encryptedPassword = passwordEncoderPort.encodePassword(user.getPassword());
        user.setPassword(encryptedPassword);

        userPersistencePort.saveUser(user);
    }
}
