package com.ocsoares.advancedcrudspringboot.infrastructure.security;

import com.ocsoares.advancedcrudspringboot.application.gateways.security.ITokenServiceGateway;
import com.ocsoares.advancedcrudspringboot.application.gateways.user.IUserRepositoryGateway;
import com.ocsoares.advancedcrudspringboot.domain.entity.UserDomainEntity;
import com.ocsoares.advancedcrudspringboot.domain.exceptions.security.ErrorJWTVerificationException;
import com.ocsoares.advancedcrudspringboot.infrastructure.mappers.UserPersistenceEntityMapper;
import com.ocsoares.advancedcrudspringboot.infrastructure.persistence.entity.UserPersistenceEntity;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

// NÃO precisa usar "@Component" ou algo desse Tipo aqui porque JÁ ESTÁ sendo INSTANCIADO por um "@Bean" em
// "SecurityConfig" anotado com "@Configuration", o que permite que outras Classes possam usar "@Autowired"
// nessa Classe para usá-la!!!
@RequiredArgsConstructor
public class SecurityFilter extends OncePerRequestFilter {
    private final ITokenServiceGateway tokenServiceGateway;
    private final IUserRepositoryGateway userRepositoryGateway;
    private final UserPersistenceEntityMapper userPersistenceEntityMapper;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain
    ) throws ServletException, IOException {
        String token = this.recoverToken(request);

        if (token != null) {
            String email = null;

            try {
                email = tokenServiceGateway.validateToken(token);
            } catch (ErrorJWTVerificationException e) {
                throw new ServletException(e.getMessage());
            }

            Optional<UserDomainEntity> userFoundByEmail = userRepositoryGateway.findUserByEmail(email);

            if (userFoundByEmail.isPresent()) {
                UserDomainEntity userDomain = userFoundByEmail.get();

                // TRANSFORMANDO o "UserDomainEntity" para "UserPersistenceEntity", porque ele É UM "UserDetails" !!!
                UserPersistenceEntity userPersistence = this.userPersistenceEntityMapper.toPersistence(userDomain);

                var authentication = new UsernamePasswordAuthenticationToken(userPersistence, null,
                        userPersistence.getAuthorities()
                );

                // SETA a Autenticação!!!
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request, response);
    }

    // Pega o Token do tipo "Bearer" dos HEADERS !!!
    private String recoverToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null) {
            return null;
        }
        return authHeader.replace("Bearer ", "");
    }
}
