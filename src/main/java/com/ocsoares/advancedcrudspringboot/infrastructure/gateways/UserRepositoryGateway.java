package com.ocsoares.advancedcrudspringboot.infrastructure.gateways;

import com.ocsoares.advancedcrudspringboot.application.gateways.user.UserGateway;
import com.ocsoares.advancedcrudspringboot.domain.entity.UserDomainEntity;
import com.ocsoares.advancedcrudspringboot.infrastructure.mappers.UserPersistenceEntityMapper;
import com.ocsoares.advancedcrudspringboot.infrastructure.persistence.entity.UserPersistenceEntity;
import com.ocsoares.advancedcrudspringboot.infrastructure.persistence.repository.jpa.JpaUserRepository;

// Classe que vai de fato IMPLEMENTAR os Métodos como SALVAR o Usuário, por exemplo, e como está na Camada de
// "Infrastructure" PODE usar recursos de Frameworks!!!
public class UserRepositoryGateway implements UserGateway {
    private final JpaUserRepository jpaUserRepository;
    private final UserPersistenceEntityMapper userPersistenceEntityMapper;

    public UserRepositoryGateway(
            JpaUserRepository jpaUserRepository, UserPersistenceEntityMapper userPersistenceEntityMapper
    ) {
        this.jpaUserRepository = jpaUserRepository;
        this.userPersistenceEntityMapper = userPersistenceEntityMapper;
    }

    @Override
    public UserDomainEntity createUser(UserDomainEntity userDomainEntity) {
        UserPersistenceEntity userPersistenceEntity = this.userPersistenceEntityMapper.toPersistence(
                userDomainEntity);

        // PRECISA CONVERTER o Usuário de DOMÍNIO (userDomainEntity) para o Usuário do BANCO de DADOS, que é
        // o "UserPersistenceEntity", usado como PARÂMETRO no "save" !!!
        UserPersistenceEntity savedUser = this.jpaUserRepository.save(userPersistenceEntity);

        // Como o Usuário SALVO no Banco de Dados é do tipo "UserPersistenceEntity", mas o RETORNO desse Método
        // é do Tipo "UserDomainEntity", PRECISA CONVERTER o "savedUser" (Persistence) para DOMAIN!!!
        return this.userPersistenceEntityMapper.toDomain(savedUser);
    }
}
