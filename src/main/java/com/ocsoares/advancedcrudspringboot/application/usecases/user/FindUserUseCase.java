package com.ocsoares.advancedcrudspringboot.application.usecases.user;

import com.ocsoares.advancedcrudspringboot.application.gateways.user.IUserRepositoryGateway;
import com.ocsoares.advancedcrudspringboot.application.usecases.interfaces.IUseCaseWithArgument;
import com.ocsoares.advancedcrudspringboot.application.usecases.mapper.UserUseCaseMapper;
import com.ocsoares.advancedcrudspringboot.application.usecases.response.UserResponse;
import com.ocsoares.advancedcrudspringboot.domain.entity.UserDomainEntity;
import com.ocsoares.advancedcrudspringboot.domain.exceptions.user.InvalidUserByIdException;

import java.util.Optional;
import java.util.UUID;

public class FindUserUseCase implements IUseCaseWithArgument<UserResponse, UUID, Exception> {
    private final IUserRepositoryGateway userRepositoryGateway;
    private final UserUseCaseMapper userUseCaseMapper;

    public FindUserUseCase(IUserRepositoryGateway userRepositoryGateway, UserUseCaseMapper userUseCaseMapper) {
        this.userRepositoryGateway = userRepositoryGateway;
        this.userUseCaseMapper = userUseCaseMapper;
    }

    @Override
    public UserResponse execute(UUID id) throws InvalidUserByIdException {
        Optional<UserDomainEntity> userFoundById = this.userRepositoryGateway.findUserById(id);

        if (userFoundById.isEmpty()) {
            throw new InvalidUserByIdException();
        }

        UserDomainEntity user = userFoundById.get();

        return this.userUseCaseMapper.toResponse(user);
    }
}
