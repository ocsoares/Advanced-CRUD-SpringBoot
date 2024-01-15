package com.ocsoares.advancedcrudspringboot.utils;

import com.ocsoares.advancedcrudspringboot.domain.entity.UserDomainEntity;

public class TestUtils {
    public static UserDomainEntity createUser() {
        return new UserDomainEntity("Bernado", "bernado@gmail.com", "bernadinho123");
    }
}
