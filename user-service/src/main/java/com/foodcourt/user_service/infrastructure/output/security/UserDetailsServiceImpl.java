package com.foodcourt.user_service.infrastructure.output.security;

import com.foodcourt.user_service.infrastructure.output.jpa.entity.UserEntity;
import com.foodcourt.user_service.infrastructure.output.jpa.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static com.foodcourt.user_service.infrastructure.utils.InfrastructureConstants.ACCOUNT_LOCKED_MESSAGE;
import static com.foodcourt.user_service.infrastructure.utils.InfrastructureConstants.USERNAME_NOT_FOUND_MESSAGE;

@Service
@Transactional
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final IUserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(USERNAME_NOT_FOUND_MESSAGE + email));

        if (userEntity.getLockTime() != null && userEntity.getLockTime().isAfter(LocalDateTime.now())) {
            throw new LockedException(ACCOUNT_LOCKED_MESSAGE);
        }

        return new UserDetailsImpl(userEntity);
    }
}
