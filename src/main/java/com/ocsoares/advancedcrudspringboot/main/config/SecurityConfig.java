package com.ocsoares.advancedcrudspringboot.main.config;

import com.ocsoares.advancedcrudspringboot.application.gateways.security.IAuthManagerGateway;
import com.ocsoares.advancedcrudspringboot.application.gateways.security.IAuthServiceGateway;
import com.ocsoares.advancedcrudspringboot.application.gateways.security.ITokenServiceGateway;
import com.ocsoares.advancedcrudspringboot.application.gateways.user.IUserGateway;
import com.ocsoares.advancedcrudspringboot.infrastructure.controllers.user.services.auth.AuthService;
import com.ocsoares.advancedcrudspringboot.infrastructure.gateways.security.AuthManager;
import com.ocsoares.advancedcrudspringboot.infrastructure.mappers.UserPersistenceEntityMapper;
import com.ocsoares.advancedcrudspringboot.infrastructure.security.FilterChainSecurity;
import com.ocsoares.advancedcrudspringboot.infrastructure.security.SecurityFilter;
import com.ocsoares.advancedcrudspringboot.infrastructure.security.services.JwtService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class SecurityConfig {
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws
            Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ITokenServiceGateway tokenServiceGateway(UserPersistenceEntityMapper userPersistenceEntityMapper) {
        return new JwtService(userPersistenceEntityMapper);
    }

    @Bean
    public SecurityFilter securityFilter(
            ITokenServiceGateway tokenServiceGateway, IUserGateway userGateway,
            UserPersistenceEntityMapper userPersistenceEntityMapper
    ) {
        return new SecurityFilter(tokenServiceGateway, userGateway, userPersistenceEntityMapper);
    }

    @Bean
    public FilterChainSecurity filterChainSecurity(SecurityFilter securityFilter) {
        return new FilterChainSecurity(securityFilter);
    }

    @Bean
    public IAuthManagerGateway<Authentication> authManagerGateway(AuthenticationManager authenticationManager) {
        return new AuthManager(authenticationManager);
    }

    @Bean
    public IAuthServiceGateway authServiceGateway(
            IUserGateway userGateway, UserPersistenceEntityMapper userPersistenceEntityMapper,
            IAuthManagerGateway<Authentication> authManagerGateway, ITokenServiceGateway tokenServiceGateway
    ) {
        return new AuthService(userGateway, userPersistenceEntityMapper, authManagerGateway, tokenServiceGateway);
    }
}