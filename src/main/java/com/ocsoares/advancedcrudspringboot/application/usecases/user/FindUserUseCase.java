package com.ocsoares.advancedcrudspringboot.application.usecases.user;

import com.ocsoares.advancedcrudspringboot.application.gateways.user.IUserGateway;
import com.ocsoares.advancedcrudspringboot.application.usecases.interfaces.IUseCaseWithArgument;
import com.ocsoares.advancedcrudspringboot.domain.entity.UserDomainEntity;
import com.ocsoares.advancedcrudspringboot.domain.exceptions.user.InvalidUserByIdException;

import java.util.Optional;
import java.util.UUID;

public class FindUserUseCase implements IUseCaseWithArgument<UserDomainEntity, UUID, Exception> {
    private final IUserGateway userGateway;

    public FindUserUseCase(IUserGateway userGateway) {
        this.userGateway = userGateway;
    }

    @Override
    public UserDomainEntity execute(UUID id) throws Exception {
        Optional<UserDomainEntity> userFoundById = this.userGateway.findUserById(id);

        if (userFoundById.isEmpty()) {
            throw new InvalidUserByIdException();
        }

        return userFoundById.get();
    }
}
