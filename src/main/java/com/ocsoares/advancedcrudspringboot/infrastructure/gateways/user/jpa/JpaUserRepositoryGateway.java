package com.ocsoares.advancedcrudspringboot.infrastructure.gateways.user.jpa;

import com.ocsoares.advancedcrudspringboot.application.gateways.user.IUserGateway;
import com.ocsoares.advancedcrudspringboot.domain.entity.UserDomainEntity;
import com.ocsoares.advancedcrudspringboot.infrastructure.mappers.UserPersistenceEntityMapper;
import com.ocsoares.advancedcrudspringboot.infrastructure.persistence.entity.UserPersistenceEntity;
import com.ocsoares.advancedcrudspringboot.infrastructure.persistence.repository.jpa.JpaUserRepository;

// Classe que vai de fato IMPLEMENTAR os Métodos como SALVAR o Usuário, por exemplo, e como está na Camada de
// "Infrastructure" PODE usar recursos de Frameworks!!!
public class JpaUserRepositoryGateway implements IUserGateway {
    private final JpaUserRepository jpaUserRepository;
    private final UserPersistenceEntityMapper userPersistenceEntityMapper;

    public JpaUserRepositoryGateway(
            JpaUserRepository jpaUserRepository, UserPersistenceEntityMapper userPersistenceEntityMapper
    ) {
        this.jpaUserRepository = jpaUserRepository;
        this.userPersistenceEntityMapper = userPersistenceEntityMapper;
    }

    @Override
    public UserDomainEntity createUser(UserDomainEntity userDomainEntity) {
        UserPersistenceEntity userPersistenceEntity = this.userPersistenceEntityMapper.toPersistence(
                userDomainEntity);

        UserPersistenceEntity savedUser = this.jpaUserRepository.save(userPersistenceEntity);

        return this.userPersistenceEntityMapper.toDomain(savedUser);
    }
}
