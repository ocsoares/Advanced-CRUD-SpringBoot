package com.ocsoares.advancedcrudspringboot.utils;

import com.ocsoares.advancedcrudspringboot.application.usecases.response.UserResponse;
import com.ocsoares.advancedcrudspringboot.domain.entity.UserDomainEntity;
import com.ocsoares.advancedcrudspringboot.infrastructure.persistence.entity.UserPersistenceEntity;

import java.util.List;
import java.util.UUID;

public class TestUtils {
    public static UserDomainEntity createUser() {
        return new UserDomainEntity("Bernado", "bernado@gmail.com", "bernadinho123");
    }

    public static UserPersistenceEntity createUserWithId() {
        var testUser = new UserPersistenceEntity("Bernado", "bernado@gmail.com", "bernadinho123");
        testUser.setId(UUID.randomUUID());

        return testUser;
    }

    public static UserDomainEntity toDomain(UserPersistenceEntity userPersistenceEntity) {
        return new UserDomainEntity(userPersistenceEntity.getName(), userPersistenceEntity.getEmail(),
                userPersistenceEntity.getPassword()
        );
    }

    public static UserPersistenceEntity toPersistence(UserDomainEntity userDomainEntity) {
        return new UserPersistenceEntity(userDomainEntity.name(), userDomainEntity.email(),
                userDomainEntity.password()
        );
    }

    public static UserResponse toResponse(UserDomainEntity userDomainEntity) {
        return new UserResponse(userDomainEntity.name(), userDomainEntity.email());
    }

    public static List<UserResponse> toResponseList(List<UserDomainEntity> userDomainEntityList) {
        return userDomainEntityList.stream().map(TestUtils::toResponse).toList();
    }
}
