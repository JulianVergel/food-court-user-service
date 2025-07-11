package com.foodcourt.user_service.infrastructure.output.jpa.adapter;

import com.foodcourt.user_service.domain.model.Role;
import com.foodcourt.user_service.domain.spi.IRolePersistencePort;
import com.foodcourt.user_service.infrastructure.output.jpa.mapper.IRoleEntityMapper;
import com.foodcourt.user_service.infrastructure.output.jpa.repository.IRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RoleJpaAdapter implements IRolePersistencePort {

    private final IRoleRepository roleRepository;
    private final IRoleEntityMapper roleEntityMapper;

    @Override
    public Role findRoleByName(String name) {
        return roleEntityMapper.toRole(roleRepository.findByName(name));
    }
}
