package com.ocsoares.advancedcrudspringboot.application.usecases.user;

import com.ocsoares.advancedcrudspringboot.application.gateways.user.IUserGateway;
import com.ocsoares.advancedcrudspringboot.application.usecases.interfaces.IUseCaseWithoutArgument;
import com.ocsoares.advancedcrudspringboot.domain.entity.UserDomainEntity;

import java.util.List;

public class FindAllUsersUseCase implements IUseCaseWithoutArgument<List<UserDomainEntity>> {
    private final IUserGateway userGateway;

    public FindAllUsersUseCase(IUserGateway userGateway) {
        this.userGateway = userGateway;
    }

    @Override
    public List<UserDomainEntity> execute() {
        return this.userGateway.findAllUsers();
    }
}
