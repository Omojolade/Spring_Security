package com.decagon.springsecurityjwt.utils;

import com.decagon.springsecurityjwt.dto.AppUserRequest;
import com.decagon.springsecurityjwt.model.AppUser;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ModelMapper {
    ModelMapper INSTANCE = Mappers.getMapper(ModelMapper.class);

    AppUser convertAppUserRequestToAppUserEntity(AppUserRequest appUserRequest);
}
