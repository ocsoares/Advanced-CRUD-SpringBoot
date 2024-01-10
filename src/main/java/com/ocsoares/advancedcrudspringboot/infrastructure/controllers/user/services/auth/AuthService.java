package com.ocsoares.advancedcrudspringboot.infrastructure.controllers.user.services.auth;

import com.ocsoares.advancedcrudspringboot.application.gateways.security.IAuthManagerGateway;
import com.ocsoares.advancedcrudspringboot.application.gateways.security.IAuthServiceGateway;
import com.ocsoares.advancedcrudspringboot.application.gateways.security.ITokenServiceGateway;
import com.ocsoares.advancedcrudspringboot.application.gateways.user.IUserGateway;
import com.ocsoares.advancedcrudspringboot.domain.entity.UserDomainEntity;
import com.ocsoares.advancedcrudspringboot.domain.exceptions.security.ErrorCreatingJWTException;
import com.ocsoares.advancedcrudspringboot.infrastructure.mappers.UserPersistenceEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

@RequiredArgsConstructor
public class AuthService implements UserDetailsService, IAuthServiceGateway {
    private final IUserGateway userGateway;
    private final UserPersistenceEntityMapper userPersistenceEntityMapper;
    private final IAuthManagerGateway<Authentication> authManagerGateway;
    private final ITokenServiceGateway tokenServiceGateway;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<UserDomainEntity> userFoundByEmail = this.userGateway.findUserByEmail(email);

        if (userFoundByEmail.isEmpty()) {
            throw new UsernameNotFoundException("Invalid credentials");
        }

        UserDomainEntity userDomainEntity = userFoundByEmail.get();

        return this.userPersistenceEntityMapper.toPersistence(userDomainEntity);
    }

    @Override
    public String login(String email, String password) throws ErrorCreatingJWTException {
        var authenticationByUsernameAndPassword = new UsernamePasswordAuthenticationToken(email, password);
        Authentication authenticatedUser = this.authManagerGateway.authenticate(authenticationByUsernameAndPassword);

        // VER se essa Conversão FUNCIONA Mesmo !!!
        return this.tokenServiceGateway.generateToken((UserDomainEntity) authenticatedUser.getPrincipal());
    }
}
