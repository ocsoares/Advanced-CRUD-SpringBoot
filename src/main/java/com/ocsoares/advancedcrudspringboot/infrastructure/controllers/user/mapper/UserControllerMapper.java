package com.ocsoares.advancedcrudspringboot.infrastructure.controllers.user.mapper;

import com.ocsoares.advancedcrudspringboot.domain.entity.UserDomainEntity;
import com.ocsoares.advancedcrudspringboot.infrastructure.controllers.user.dtos.CreateUserDTO;
import com.ocsoares.advancedcrudspringboot.infrastructure.controllers.user.response.UserResponse;

import java.util.List;

public class UserControllerMapper {
    public UserDomainEntity toDomain(CreateUserDTO createUserDTO) {
        return new UserDomainEntity(createUserDTO.name(), createUserDTO.email(), createUserDTO.password());
    }

    public UserResponse toResponse(UserDomainEntity userDomainEntity) {
        return new UserResponse(userDomainEntity.name(), userDomainEntity.email());
    }

    public List<UserResponse> toResponseList(List<UserDomainEntity> userDomainEntityList) {
        return userDomainEntityList.stream().map(this::toResponse).toList();
    }
}
