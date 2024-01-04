package com.ocsoares.advancedcrudspringboot.infrastructure.controllers.user.mapper;

import com.ocsoares.advancedcrudspringboot.domain.entity.UserDomainEntity;
import com.ocsoares.advancedcrudspringboot.infrastructure.controllers.user.dtos.UserDTO;
import com.ocsoares.advancedcrudspringboot.infrastructure.controllers.user.response.UserResponse;

import java.util.List;

public class UserControllerMapper {
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
