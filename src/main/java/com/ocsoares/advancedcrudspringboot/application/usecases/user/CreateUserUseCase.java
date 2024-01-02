package com.ocsoares.advancedcrudspringboot.application.usecases.user;

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

    public CreateUserUseCase(IUserGateway userGateway) {
        this.userGateway = userGateway;
    }

    @Override
    public UserDomainEntity execute(UserDomainEntity userEntity) {
        return this.userGateway.createUser(userEntity);
    }
}
