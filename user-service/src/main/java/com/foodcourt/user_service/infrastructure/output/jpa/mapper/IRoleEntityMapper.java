package com.foodcourt.user_service.infrastructure.output.jpa.mapper;

import com.foodcourt.user_service.domain.model.Role;
import com.foodcourt.user_service.infrastructure.output.jpa.entity.RoleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IRoleEntityMapper {
    RoleEntity toRoleEntity(Role role);
    Role toRole(RoleEntity roleEntity);
}
