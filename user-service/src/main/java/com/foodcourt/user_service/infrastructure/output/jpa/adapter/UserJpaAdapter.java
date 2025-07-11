package com.foodcourt.user_service.infrastructure.output.jpa.adapter;

import com.foodcourt.user_service.domain.model.User;
import com.foodcourt.user_service.domain.spi.IUserPersistencePort;
import com.foodcourt.user_service.infrastructure.output.jpa.entity.UserEntity;
import com.foodcourt.user_service.infrastructure.output.jpa.mapper.IUserEntityMapper;
import com.foodcourt.user_service.infrastructure.output.jpa.repository.IUserRepository;
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
}
