package com.ocsoares.advancedcrudspringboot.application.usecases.user;

import com.ocsoares.advancedcrudspringboot.application.gateways.user.IUserRepositoryGateway;
import com.ocsoares.advancedcrudspringboot.application.usecases.interfaces.IUseCaseWithArgument;
import com.ocsoares.advancedcrudspringboot.domain.entity.UserDomainEntity;
import com.ocsoares.advancedcrudspringboot.domain.exceptions.user.InvalidUserByIdException;

import java.util.Optional;
import java.util.UUID;

public class DeleteUserUseCase implements IUseCaseWithArgument<Void, UUID, Exception> {
    private final IUserRepositoryGateway userRepositoryGateway;

    public DeleteUserUseCase(IUserRepositoryGateway userRepositoryGateway) {
        this.userRepositoryGateway = userRepositoryGateway;
    }

    @Override
    public Void execute(UUID id) throws InvalidUserByIdException {
        Optional<UserDomainEntity> foundUserById = this.userRepositoryGateway.findUserById(id);

        if (foundUserById.isEmpty()) {
            throw new InvalidUserByIdException();
        }

        return this.userRepositoryGateway.deleteUserById(id);
    }
}
