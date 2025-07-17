package com.foodcourt.user_service.infrastructure.output.jpa.adapter;

import com.foodcourt.user_service.domain.model.User;
import com.foodcourt.user_service.domain.spi.IUserPersistencePort;
import com.foodcourt.user_service.infrastructure.output.jpa.entity.UserEntity;
import com.foodcourt.user_service.infrastructure.output.jpa.mapper.IUserEntityMapper;
import com.foodcourt.user_service.infrastructure.output.jpa.repository.IUserRepository;
import com.foodcourt.user_service.infrastructure.exception.NoDataFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserJpaAdapter implements IUserPersistencePort {

    private final IUserRepository userRepository;
    private final IUserEntityMapper userEntityMapper;

    @Override
    public User saveUser(User user) {
        UserEntity userEntity = userEntityMapper.toUserEntity(user);
        var userSavedEntity = userRepository.save(userEntity);
        return userEntityMapper.toUser(userSavedEntity);
    }

    @Override
    public boolean existsByDocument(String document) {
        return userRepository.existsByDocument(document);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public boolean existsByPhone(String phone) {
        return userRepository.existsByPhone(phone);
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id)
                .map(userEntityMapper::toUser)
                .orElseThrow(NoDataFoundException::new);
    }
}
