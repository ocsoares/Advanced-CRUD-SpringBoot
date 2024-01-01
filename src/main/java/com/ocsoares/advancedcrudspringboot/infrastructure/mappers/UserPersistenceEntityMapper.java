package com.ocsoares.advancedcrudspringboot.infrastructure.mappers;

import com.ocsoares.advancedcrudspringboot.domain.entity.UserDomainEntity;
import com.ocsoares.advancedcrudspringboot.infrastructure.persistence.entity.UserPersistenceEntity;

public class UserPersistenceEntityMapper {
    public UserPersistenceEntity toPersistenceEntity(UserDomainEntity userDomainEntity) {
        return new UserPersistenceEntity(
                userDomainEntity.name(), userDomainEntity.email(), userDomainEntity.password());
    }

    public UserDomainEntity toDomainEntity(UserPersistenceEntity userPersistenceEntity) {
        return new UserDomainEntity(userPersistenceEntity.getName(), userPersistenceEntity.getEmail(),
                                    userPersistenceEntity.getPassword()
        );
    }
}