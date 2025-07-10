package com.foodcourt.user_service.infrastructure.adapter.out.persistence.repository;

import com.foodcourt.user_service.infrastructure.adapter.out.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataUserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);
    Optional<UserEntity> findByDocument(String documentId);
    boolean existsByEmail(String email); // Para verificar unicidad
    boolean existsByDocument(String documentId); // Para verificar unicidad
}
