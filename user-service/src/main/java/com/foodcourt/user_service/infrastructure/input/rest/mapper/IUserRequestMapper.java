package com.foodcourt.user_service.infrastructure.input.rest.mapper;

import com.foodcourt.user_service.domain.model.User;
import com.foodcourt.user_service.infrastructure.input.rest.dto.request.UserRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", // Para que Spring pueda inyectarlo
        unmappedTargetPolicy = ReportingPolicy.IGNORE, // Ignora campos no mapeados
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IUserRequestMapper {
    User toUser(UserRequestDto userRequestDto);
}
