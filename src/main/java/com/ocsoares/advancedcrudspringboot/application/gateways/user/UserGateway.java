package com.ocsoares.advancedcrudspringboot.application.gateways.user;

import com.ocsoares.advancedcrudspringboot.domain.entity.UserDomainEntity;

// Esse "UserGateway" vai ser um PORTÃO de ENTRADA (uma Abstração) para a Classe em uma Camada mais
// EXTERNA que de Fato vai ser Responsável pelos Métodos de SALVAR o Usuário, por exemplo!!
public interface UserGateway {
    UserDomainEntity createUser(UserDomainEntity userDomainEntity);
}
