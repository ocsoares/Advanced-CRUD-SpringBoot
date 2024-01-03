package com.ocsoares.advancedcrudspringboot.application.usecases.user;

import com.ocsoares.advancedcrudspringboot.application.gateways.security.PasswordHasherGateway;
import com.ocsoares.advancedcrudspringboot.application.gateways.user.IUserGateway;
import com.ocsoares.advancedcrudspringboot.application.usecases.interfaces.IUseCaseWithArgument;
import com.ocsoares.advancedcrudspringboot.domain.entity.UserDomainEntity;
import org.apache.coyote.BadRequestException;

import java.util.Optional;

// NÃO usar nenhum Recurso de Frameworks, como "annotations" de algum Framework, porque essa Pasta é
// apenas a REGRA DE NEGÓCIO da Aplicação, APENAS com Código JAVA!!!
// ----------------------------------------------------------
// Esse é o SERVICE que vai ser usado pelo Controller!!!
// ----------------------------------------------------------
// TROCAR esse SEGUNDO "UserDomainEntity" por um DTO!!
public class CreateUserUseCase implements IUseCaseWithArgument<UserDomainEntity, UserDomainEntity, Exception> {
    private final IUserGateway userGateway;
    private final PasswordHasherGateway passwordHasherGateway;

    public CreateUserUseCase(IUserGateway userGateway, PasswordHasherGateway passwordHasherGateway) {
        this.userGateway = userGateway;
        this.passwordHasherGateway = passwordHasherGateway;
    }

    @Override
    public UserDomainEntity execute(UserDomainEntity userEntity) throws BadRequestException {
        Optional<UserDomainEntity> userAlreadyExists = this.userGateway.findUserByEmail(userEntity.email());

        if (userAlreadyExists.isPresent()) {
            throw new BadRequestException("There is already a user registered with this email !");
        }

        String hashedPassword = this.passwordHasherGateway.hash(userEntity.password());

        UserDomainEntity updatedUserWithHashedPassword = new UserDomainEntity(
                userEntity.name(), userEntity.email(), hashedPassword);

        return this.userGateway.createUser(updatedUserWithHashedPassword);

    }
}
