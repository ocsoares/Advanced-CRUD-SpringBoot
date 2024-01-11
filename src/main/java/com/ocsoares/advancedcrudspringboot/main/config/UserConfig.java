package com.ocsoares.advancedcrudspringboot.main.config;

import com.ocsoares.advancedcrudspringboot.application.gateways.security.IAuthServiceGateway;
import com.ocsoares.advancedcrudspringboot.application.gateways.security.PasswordHasherGateway;
import com.ocsoares.advancedcrudspringboot.application.gateways.user.IUserGateway;
import com.ocsoares.advancedcrudspringboot.application.usecases.user.*;
import com.ocsoares.advancedcrudspringboot.infrastructure.controllers.user.LoginUserController;
import com.ocsoares.advancedcrudspringboot.infrastructure.controllers.user.mapper.UserControllerMapper;
import com.ocsoares.advancedcrudspringboot.infrastructure.gateways.user.jpa.JpaUserRepositoryGateway;
import com.ocsoares.advancedcrudspringboot.infrastructure.mappers.UserPersistenceEntityMapper;
import com.ocsoares.advancedcrudspringboot.infrastructure.persistence.repository.jpa.JpaUserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// Camada para aplicar a Injeção de Dependência, e estou usando o Spring Boot, mas pode ser facilmente
// TROCADO por outra Lógica ou Framework, separando da REGRA de NEGÓCIO da Aplicação!!!
@Configuration
public class UserConfig {
    @Bean
    public CreateUserUseCase createUserUseCase(IUserGateway userGateway, PasswordHasherGateway passwordHasherGateway) {
        return new CreateUserUseCase(userGateway, passwordHasherGateway);
    }

    @Bean
    public FindAllUsersUseCase findAllUsersUseCase(IUserGateway userGateway) {
        return new FindAllUsersUseCase(userGateway);
    }

    @Bean
    public FindUserUseCase findUserUseCase(IUserGateway userGateway) {
        return new FindUserUseCase(userGateway);
    }

    @Bean
    public UpdateUserUseCase updateUserUseCase(IUserGateway userGateway, PasswordHasherGateway passwordHasherGateway) {
        return new UpdateUserUseCase(userGateway, passwordHasherGateway);
    }

    @Bean
    public DeleteUserUseCase deleteUserUseCase(IUserGateway userGateway) {
        return new DeleteUserUseCase(userGateway);
    }

    @Bean
    public LoginUserController loginUserController(IAuthServiceGateway authServiceGateway) {
        return new LoginUserController(authServiceGateway);
    }

    @Bean
    public IUserGateway userGateway(
            JpaUserRepository jpaUserRepository, UserPersistenceEntityMapper userPersistenceEntityMapper
    ) {
        return new JpaUserRepositoryGateway(jpaUserRepository, userPersistenceEntityMapper);
    }
    
    @Bean
    public UserPersistenceEntityMapper userPersistenceEntityMapper() {
        return new UserPersistenceEntityMapper();
    }

    @Bean
    public UserControllerMapper userControllerMapper() {
        return new UserControllerMapper();
    }
}
