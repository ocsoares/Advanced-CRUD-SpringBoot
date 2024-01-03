package com.ocsoares.advancedcrudspringboot.main.config;

import com.ocsoares.advancedcrudspringboot.application.gateways.security.PasswordHasherGateway;
import com.ocsoares.advancedcrudspringboot.application.gateways.user.IUserGateway;
import com.ocsoares.advancedcrudspringboot.application.usecases.user.CreateUserUseCase;
import com.ocsoares.advancedcrudspringboot.infrastructure.controllers.user.mapper.UserDTOMapper;
import com.ocsoares.advancedcrudspringboot.infrastructure.gateways.security.bcrypt.BcryptHasher;
import com.ocsoares.advancedcrudspringboot.infrastructure.gateways.user.jpa.JpaUserRepositoryGateway;
import com.ocsoares.advancedcrudspringboot.infrastructure.mappers.UserPersistenceEntityMapper;
import com.ocsoares.advancedcrudspringboot.infrastructure.persistence.repository.jpa.JpaUserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

// Camada para aplicar a Injeção de Dependência, e estou usando o Spring Boot, mas pode ser facilmente
// TROCADO por outra Lógica ou Framework, separando da REGRA de NEGÓCIO da Aplicação!!!
@Configuration
public class UserConfig {
    @Bean
    public CreateUserUseCase createUserUseCase(IUserGateway userGateway, PasswordHasherGateway passwordHasherGateway) {
        return new CreateUserUseCase(userGateway, passwordHasherGateway);
    }

    @Bean
    public IUserGateway userGateway(
            JpaUserRepository jpaUserRepository, UserPersistenceEntityMapper userPersistenceEntityMapper
    ) {
        return new JpaUserRepositoryGateway(jpaUserRepository, userPersistenceEntityMapper);
    }

    @Bean
    public PasswordHasherGateway passwordHasherGateway(BCryptPasswordEncoder bCryptPasswordEncoder) {
        return new BcryptHasher(bCryptPasswordEncoder);
    }

    @Bean
    public UserPersistenceEntityMapper userPersistenceEntityMapper() {
        return new UserPersistenceEntityMapper();
    }

    @Bean
    public UserDTOMapper userDTOMapper() {
        return new UserDTOMapper();
    }
}