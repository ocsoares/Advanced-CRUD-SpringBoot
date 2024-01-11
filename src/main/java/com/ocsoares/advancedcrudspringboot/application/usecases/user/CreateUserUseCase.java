package com.ocsoares.advancedcrudspringboot.application.usecases.user;

import com.ocsoares.advancedcrudspringboot.application.gateways.security.PasswordHasherGateway;
import com.ocsoares.advancedcrudspringboot.application.gateways.user.IUserRepositoryGateway;
import com.ocsoares.advancedcrudspringboot.application.usecases.interfaces.IUseCaseWithArgument;
import com.ocsoares.advancedcrudspringboot.domain.entity.UserDomainEntity;
import com.ocsoares.advancedcrudspringboot.domain.exceptions.user.UserAlreadyExistsByEmailException;

import java.util.Optional;

// NÃO usar nenhum Recurso de Frameworks, como "annotations" de algum Framework, porque essa Pasta é
// apenas a REGRA DE NEGÓCIO da Aplicação, APENAS com Código JAVA!!!
// ----------------------------------------------------------
// Esse é o SERVICE que vai ser usado pelo Controller!!!
// ----------------------------------------------------------
// TROCAR esse SEGUNDO "UserDomainEntity" por um DTO!!
public class CreateUserUseCase implements IUseCaseWithArgument<UserDomainEntity, UserDomainEntity, Exception> {
    private final IUserRepositoryGateway userRepositoryGateway;
    private final PasswordHasherGateway passwordHasherGateway;

    public CreateUserUseCase(
            IUserRepositoryGateway userRepositoryGateway, PasswordHasherGateway passwordHasherGateway
    ) {
        this.userRepositoryGateway = userRepositoryGateway;
        this.passwordHasherGateway = passwordHasherGateway;
    }

    @Override
    public UserDomainEntity execute(UserDomainEntity userEntity) throws UserAlreadyExistsByEmailException {
        Optional<UserDomainEntity> userAlreadyExists = this.userRepositoryGateway.findUserByEmail(userEntity.email());

        if (userAlreadyExists.isPresent()) {
            throw new UserAlreadyExistsByEmailException();
        }

        String hashedPassword = this.passwordHasherGateway.hash(userEntity.password());

        UserDomainEntity userWithHashedPassword = new UserDomainEntity(userEntity.name(), userEntity.email(),
                hashedPassword
        );

        return this.userRepositoryGateway.createUser(userWithHashedPassword);

    }
}
