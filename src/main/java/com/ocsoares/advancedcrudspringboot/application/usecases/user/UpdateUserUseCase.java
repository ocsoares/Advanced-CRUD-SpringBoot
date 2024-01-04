package com.ocsoares.advancedcrudspringboot.application.usecases.user;

import com.ocsoares.advancedcrudspringboot.application.gateways.security.PasswordHasherGateway;
import com.ocsoares.advancedcrudspringboot.application.gateways.user.IUserGateway;
import com.ocsoares.advancedcrudspringboot.application.usecases.interfaces.IUseCaseWithTwoArguments;
import com.ocsoares.advancedcrudspringboot.domain.entity.UserDomainEntity;
import com.ocsoares.advancedcrudspringboot.domain.exceptions.user.InvalidUserByIdException;

import java.util.Optional;
import java.util.UUID;

public class UpdateUserUseCase implements IUseCaseWithTwoArguments<UserDomainEntity, UUID, UserDomainEntity, Exception> {
    private final IUserGateway userGateway;
    private final PasswordHasherGateway passwordHasherGateway;

    public UpdateUserUseCase(IUserGateway userGateway, PasswordHasherGateway passwordHasherGateway) {
        this.userGateway = userGateway;
        this.passwordHasherGateway = passwordHasherGateway;
    }

    @Override
    public UserDomainEntity execute(UUID id, UserDomainEntity userDomainEntity) throws InvalidUserByIdException {
        Optional<UserDomainEntity> userFoundById = this.userGateway.findUserById(id);

        if (userFoundById.isEmpty()) {
            throw new InvalidUserByIdException();
        }

        String hashedPassword = this.passwordHasherGateway.hash(userDomainEntity.password());

        UserDomainEntity userDomainWithHashedPassword = new UserDomainEntity(
                userDomainEntity.name(), userDomainEntity.email(), hashedPassword);

        this.userGateway.updateUserById(id, userDomainWithHashedPassword);

        return null;
    }
}
