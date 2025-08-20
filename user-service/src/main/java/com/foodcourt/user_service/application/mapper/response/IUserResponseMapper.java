package com.foodcourt.user_service.application.mapper.response;

import com.foodcourt.user_service.application.dto.response.UserResponseDto;
import com.foodcourt.user_service.domain.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IUserResponseMapper {
    @Mapping(source = "restaurantId", target = "restaurantId")
    UserResponseDto toUserResponseDto(User user);
}