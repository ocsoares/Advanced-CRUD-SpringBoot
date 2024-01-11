package com.ocsoares.advancedcrudspringboot.application.usecases.user;

import com.ocsoares.advancedcrudspringboot.application.gateways.user.IUserRepositoryGateway;
import com.ocsoares.advancedcrudspringboot.application.usecases.interfaces.IUseCaseWithoutArgument;
import com.ocsoares.advancedcrudspringboot.domain.entity.UserDomainEntity;

import java.util.List;

public class FindAllUsersUseCase implements IUseCaseWithoutArgument<List<UserDomainEntity>> {
    private final IUserRepositoryGateway userRepositoryGateway;

    public FindAllUsersUseCase(IUserRepositoryGateway userRepositoryGateway) {
        this.userRepositoryGateway = userRepositoryGateway;
    }

    @Override
    public List<UserDomainEntity> execute() {
        return this.userRepositoryGateway.findAllUsers();
    }
}
