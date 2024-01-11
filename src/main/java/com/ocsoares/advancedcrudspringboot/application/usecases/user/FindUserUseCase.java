package com.ocsoares.advancedcrudspringboot.application.usecases.user;

import com.ocsoares.advancedcrudspringboot.application.gateways.user.IUserRepositoryGateway;
import com.ocsoares.advancedcrudspringboot.application.usecases.interfaces.IUseCaseWithArgument;
import com.ocsoares.advancedcrudspringboot.domain.entity.UserDomainEntity;
import com.ocsoares.advancedcrudspringboot.domain.exceptions.user.InvalidUserByIdException;

import java.util.Optional;
import java.util.UUID;

public class FindUserUseCase implements IUseCaseWithArgument<UserDomainEntity, UUID, Exception> {
    private final IUserRepositoryGateway userRepositoryGateway;

    public FindUserUseCase(IUserRepositoryGateway userRepositoryGateway) {
        this.userRepositoryGateway = userRepositoryGateway;
    }

    @Override
    public UserDomainEntity execute(UUID id) throws Exception {
        Optional<UserDomainEntity> userFoundById = this.userRepositoryGateway.findUserById(id);

        if (userFoundById.isEmpty()) {
            throw new InvalidUserByIdException();
        }

        return userFoundById.get();
    }
}
