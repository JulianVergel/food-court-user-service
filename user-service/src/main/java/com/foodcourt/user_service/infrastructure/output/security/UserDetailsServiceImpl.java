package com.foodcourt.user_service.infrastructure.output.security;

import com.foodcourt.user_service.infrastructure.output.jpa.entity.UserEntity;
import com.foodcourt.user_service.infrastructure.output.jpa.repository.IUserRepository;
import com.foodcourt.user_service.infrastructure.utils.InfrastructureConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final IUserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(InfrastructureConstants.USERNAME_NOT_FOUND_MESSAGE + email));

        return new UserDetailsImpl(userEntity);
    }
}
