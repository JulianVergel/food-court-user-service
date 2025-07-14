package com.foodcourt.user_service.domain.usecase;

import com.foodcourt.user_service.domain.api.IUserServicePort;
import com.foodcourt.user_service.domain.exception.UserAlreadyExistsException;
import com.foodcourt.user_service.domain.exception.UserIsNotOfLegalAgeException;
import com.foodcourt.user_service.domain.model.Role;
import com.foodcourt.user_service.domain.model.User;
import com.foodcourt.user_service.domain.spi.IPasswordEncoderPort;
import com.foodcourt.user_service.domain.spi.IRolePersistencePort;
import com.foodcourt.user_service.domain.spi.IUserPersistencePort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserUseCase implements IUserServicePort {

    private final IUserPersistencePort userPersistencePort;
    private final IPasswordEncoderPort passwordEncoderPort;
    private final IRolePersistencePort rolePersistencePort;

    @Override
    public void createOwner(User user) {
        if (!user.isOfLegalAge()) {
            throw new UserIsNotOfLegalAgeException();
        }
        if (userPersistencePort.existsByDocument(user.getDocument())) {
            throw new UserAlreadyExistsException("El documento ya está registrado");
        }
        if (userPersistencePort.existsByEmail(user.getEmail())) {
            throw new UserAlreadyExistsException("El correo ya está registrado");
        }
        if (userPersistencePort.existsByPhone(user.getPhone())) {
            throw new UserAlreadyExistsException("El celular ya está registrado");
        }

        Role roleOwner = rolePersistencePort.findRoleByName("Propietario");
        user.setRole(roleOwner);

        String encryptedPassword = passwordEncoderPort.encodePassword(user.getPassword());
        user.setPassword(encryptedPassword);

        userPersistencePort.saveUser(user);
    }
}
