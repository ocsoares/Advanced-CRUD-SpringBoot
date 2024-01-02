package com.ocsoares.advancedcrudspringboot.main;

import com.ocsoares.advancedcrudspringboot.application.gateways.user.IUserGateway;
import com.ocsoares.advancedcrudspringboot.application.usecases.user.CreateUserUseCase;
import com.ocsoares.advancedcrudspringboot.infrastructure.controllers.user.mapper.UserDTOMapper;
import com.ocsoares.advancedcrudspringboot.infrastructure.gateways.UserRepositoryGateway;
import com.ocsoares.advancedcrudspringboot.infrastructure.mappers.UserPersistenceEntityMapper;
import com.ocsoares.advancedcrudspringboot.infrastructure.persistence.repository.jpa.JpaUserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// Camada para aplicar a Injeção de Dependência, e estou usando o Spring Boot, mas pode ser facilmente
// TROCADO por outra Lógica ou Framework, separando da REGRA de NEGÓCIO da Aplicação!!!
@Configuration
public class UserConfig {
    @Bean
    CreateUserUseCase createUserUseCase(IUserGateway userGateway) {
        return new CreateUserUseCase(userGateway);
    }

    @Bean
    IUserGateway userGateway(
            JpaUserRepository jpaUserRepository, UserPersistenceEntityMapper userPersistenceEntityMapper
    ) {
        return new UserRepositoryGateway(jpaUserRepository, userPersistenceEntityMapper);
    }

    @Bean
    UserPersistenceEntityMapper userPersistenceEntityMapper() {
        return new UserPersistenceEntityMapper();
    }

    @Bean
    UserDTOMapper userDTOMapper() {
        return new UserDTOMapper();
    }
}
