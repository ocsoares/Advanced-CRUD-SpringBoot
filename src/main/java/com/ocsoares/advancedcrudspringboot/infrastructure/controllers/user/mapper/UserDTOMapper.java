package com.ocsoares.advancedcrudspringboot.infrastructure.controllers.user.mapper;

import com.ocsoares.advancedcrudspringboot.domain.entity.UserDomainEntity;
import com.ocsoares.advancedcrudspringboot.infrastructure.controllers.user.dtos.CreateUserDTO;
import com.ocsoares.advancedcrudspringboot.infrastructure.controllers.user.response.CreateUserResponse;

public class UserDTOMapper {
    public UserDomainEntity toDomain(CreateUserDTO createUserDTO) {
        return new UserDomainEntity(createUserDTO.name(), createUserDTO.email(), createUserDTO.password());
    }

    public CreateUserResponse toResponse(UserDomainEntity userDomainEntity) {
        return new CreateUserResponse(userDomainEntity.name(), userDomainEntity.password());
    }
}
