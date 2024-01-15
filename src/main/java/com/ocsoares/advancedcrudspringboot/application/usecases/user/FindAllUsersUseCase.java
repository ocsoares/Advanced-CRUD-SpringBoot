package com.ocsoares.advancedcrudspringboot.application.usecases.user;

import com.ocsoares.advancedcrudspringboot.application.gateways.user.IUserRepositoryGateway;
import com.ocsoares.advancedcrudspringboot.application.usecases.interfaces.IUseCaseWithoutArgument;
import com.ocsoares.advancedcrudspringboot.application.usecases.mapper.UserUseCaseMapper;
import com.ocsoares.advancedcrudspringboot.application.usecases.response.UserResponse;
import com.ocsoares.advancedcrudspringboot.domain.entity.UserDomainEntity;

import java.util.List;

public class FindAllUsersUseCase implements IUseCaseWithoutArgument<List<UserResponse>> {
    private final IUserRepositoryGateway userRepositoryGateway;
    private final UserUseCaseMapper userUseCaseMapper;

    public FindAllUsersUseCase(IUserRepositoryGateway userRepositoryGateway, UserUseCaseMapper userUseCaseMapper) {
        this.userRepositoryGateway = userRepositoryGateway;
        this.userUseCaseMapper = userUseCaseMapper;
    }

    @Override
    public List<UserResponse> execute() {
        List<UserDomainEntity> allUsersFound = this.userRepositoryGateway.findAllUsers();
        
        return this.userUseCaseMapper.toResponseList(allUsersFound);
    }
}
