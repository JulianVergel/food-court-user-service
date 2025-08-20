package com.foodcourt.user_service.domain.usecase;

import com.foodcourt.user_service.domain.api.IUserServicePort;
import com.foodcourt.user_service.domain.exception.UserAlreadyExistsException;
import com.foodcourt.user_service.domain.model.Role;
import com.foodcourt.user_service.domain.model.User;
import com.foodcourt.user_service.domain.spi.IPasswordEncoderPort;
import com.foodcourt.user_service.domain.spi.IRolePersistencePort;
import com.foodcourt.user_service.domain.spi.IUserPersistencePort;
import com.foodcourt.user_service.domain.utils.constants.DomainConstants;
import com.foodcourt.user_service.domain.utils.validators.UserValidator;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserUseCase implements IUserServicePort {

    private final IUserPersistencePort userPersistencePort;
    private final IPasswordEncoderPort passwordEncoderPort;
    private final IRolePersistencePort rolePersistencePort;

    @Override
    public void createOwner(User user) {
        createUserWithRole(user, DomainConstants.ROLE_OWNER);
    }

    @Override
    public void createEmployee(User user) {
        createUserWithRole(user, DomainConstants.ROLE_EMPLOYEE);
    }

    @Override
    public void createClient(User user) {
        createUserWithRole(user, DomainConstants.ROLE_CLIENT);
    }

    @Override
    public User getUserById(Long id) {
        return userPersistencePort.findById(id);
    }

    private void createUserWithRole(User user, String roleName) {
        UserValidator.validateUser(user);

        if (userPersistencePort.existsByDocument(user.getDocument())) {
            throw new UserAlreadyExistsException(DomainConstants.USER_ALREADY_EXISTS_DOCUMENT_MESSAGE);
        }
        if (userPersistencePort.existsByEmail(user.getEmail())) {
            throw new UserAlreadyExistsException(DomainConstants.USER_ALREADY_EXISTS_EMAIL_MESSAGE);
        }
        if (userPersistencePort.existsByPhone(user.getPhone())) {
            throw new UserAlreadyExistsException(DomainConstants.USER_ALREADY_EXISTS_PHONE_MESSAGE);
        }

        Role role = rolePersistencePort.findRoleByName(roleName);
        user.setRole(role);

        String encryptedPassword = passwordEncoderPort.encodePassword(user.getPassword());
        user.setPassword(encryptedPassword);

        userPersistencePort.saveUser(user);
    }
}
