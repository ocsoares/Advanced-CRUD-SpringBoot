package com.ocsoares.advancedcrudspringboot.infrastructure.controllers.user.mapper;

import com.ocsoares.advancedcrudspringboot.domain.entity.UserDomainEntity;
import com.ocsoares.advancedcrudspringboot.infrastructure.controllers.user.dtos.CreateUserDTO;
import com.ocsoares.advancedcrudspringboot.infrastructure.controllers.user.response.CreateUserResponse;

import java.util.List;

public class UserControllerMapper {
    public UserDomainEntity toDomain(CreateUserDTO createUserDTO) {
        return new UserDomainEntity(createUserDTO.name(), createUserDTO.email(), createUserDTO.password());
    }

    public CreateUserResponse toResponse(UserDomainEntity userDomainEntity) {
        return new CreateUserResponse(userDomainEntity.name(), userDomainEntity.email());
    }

    public List<CreateUserResponse> toResponseList(List<UserDomainEntity> userDomainEntityList) {
        return userDomainEntityList.stream().map(this::toResponse).toList();
    }
}
