package com.foodcourt.user_service.infrastructure.output.security.listener;

import com.foodcourt.user_service.infrastructure.output.jpa.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class AuthenticationEvents {

    @Value("${login.max-attempts}")
    private Integer maxAttempts;

    @Value("${login.lockout-duration-minutes}")
    private Long lockoutDurationMinutes;

    private final IUserRepository userRepository;

    @EventListener
    public void onAuthenticationSuccess(AuthenticationSuccessEvent event) {
        // El 'principal' es el username, que en nuestro caso es el email
        String email = event.getAuthentication().getName();
        userRepository.findByEmail(email).ifPresent(user -> {
            user.setFailedLoginAttempts(0);
            user.setLockTime(null); // Desbloquea la cuenta si estaba bloqueada
            userRepository.save(user);
        });
    }

    @EventListener
    public void onAuthenticationFailure(AuthenticationFailureBadCredentialsEvent event) {
        String email = event.getAuthentication().getName();
        userRepository.findByEmail(email).ifPresent(user -> {
            int attempts = user.getFailedLoginAttempts() + 1;
            user.setFailedLoginAttempts(attempts);

            if (attempts >= maxAttempts) {
                user.setLockTime(LocalDateTime.now().plusMinutes(lockoutDurationMinutes));
            }
            userRepository.save(user);
        });
    }
}
