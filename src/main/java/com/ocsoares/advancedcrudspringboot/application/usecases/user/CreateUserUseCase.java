package com.ocsoares.advancedcrudspringboot.application.usecases.user;

import com.ocsoares.advancedcrudspringboot.application.gateways.security.PasswordHasherGateway;
import com.ocsoares.advancedcrudspringboot.application.gateways.user.IUserGateway;
import com.ocsoares.advancedcrudspringboot.application.usecases.interfaces.IUseCaseWithArgument;
import com.ocsoares.advancedcrudspringboot.domain.entity.UserDomainEntity;

// NÃO usar nenhum Recurso de Frameworks, como "annotations" de algum Framework, porque essa Pasta é
// apenas a REGRA DE NEGÓCIO da Aplicação, APENAS com Código JAVA!!!
// ----------------------------------------------------------
// Esse é o SERVICE que vai ser usado pelo Controller!!!
// ----------------------------------------------------------
// TROCAR esse SEGUNDO "UserDomainEntity" por um DTO!!
public class CreateUserUseCase implements IUseCaseWithArgument<UserDomainEntity, UserDomainEntity> {
    private final IUserGateway userGateway;
    private final PasswordHasherGateway passwordHasherGateway;

    public CreateUserUseCase(IUserGateway userGateway, PasswordHasherGateway passwordHasherGateway) {
        this.userGateway = userGateway;
        this.passwordHasherGateway = passwordHasherGateway;
    }

    @Override
    public UserDomainEntity execute(UserDomainEntity userEntity) {
        String hashedPassword = this.passwordHasherGateway.hash(userEntity.password());
        
        UserDomainEntity updatedUserWithHashedPassword = new UserDomainEntity(
                userEntity.name(), userEntity.email(), hashedPassword);

        return this.userGateway.createUser(updatedUserWithHashedPassword);
    }
}
