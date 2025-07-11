package com.foodcourt.user_service.infrastructure.output.jpa.repository;

import com.foodcourt.user_service.infrastructure.output.jpa.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRoleRepository extends JpaRepository<RoleEntity, Long> {
    RoleEntity findByName(String name);
}
