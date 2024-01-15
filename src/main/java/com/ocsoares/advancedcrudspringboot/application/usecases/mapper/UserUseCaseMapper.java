package com.ocsoares.advancedcrudspringboot.application.usecases.mapper;

import com.ocsoares.advancedcrudspringboot.application.usecases.response.UserResponse;
import com.ocsoares.advancedcrudspringboot.domain.entity.UserDomainEntity;
import com.ocsoares.advancedcrudspringboot.infrastructure.controllers.user.dtos.UserDTO;

import java.util.List;

public class UserUseCaseMapper {
    public UserDomainEntity toDomain(UserDTO userDTO) {
        return new UserDomainEntity(userDTO.name(), userDTO.email(), userDTO.password());
    }

    public UserResponse toResponse(UserDomainEntity userDomainEntity) {
        return new UserResponse(userDomainEntity.name(), userDomainEntity.email());
    }

    public List<UserResponse> toResponseList(List<UserDomainEntity> userDomainEntityList) {
        return userDomainEntityList.stream().map(this::toResponse).toList();
    }
}