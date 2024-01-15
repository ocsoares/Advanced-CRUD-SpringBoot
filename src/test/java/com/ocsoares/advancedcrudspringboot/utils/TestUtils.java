package com.ocsoares.advancedcrudspringboot.utils;

import com.ocsoares.advancedcrudspringboot.domain.entity.UserDomainEntity;
import com.ocsoares.advancedcrudspringboot.infrastructure.persistence.entity.UserPersistenceEntity;

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
}
