package com.ocsoares.advancedcrudspringboot.infrastructure.security.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.ocsoares.advancedcrudspringboot.application.gateways.security.ITokenServiceGateway;
import com.ocsoares.advancedcrudspringboot.domain.entity.UserDomainEntity;
import com.ocsoares.advancedcrudspringboot.domain.exceptions.security.ErrorCreatingJWTException;
import com.ocsoares.advancedcrudspringboot.domain.exceptions.security.ErrorJWTVerificationException;
import com.ocsoares.advancedcrudspringboot.infrastructure.mappers.UserPersistenceEntityMapper;
import com.ocsoares.advancedcrudspringboot.infrastructure.persistence.entity.UserPersistenceEntity;
import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@RequiredArgsConstructor
public class JwtService implements ITokenServiceGateway {
    private static final String JWT_SECRET = System.getenv("JWT_SECRET");
    private static final String JWT_ISSUER = "advanced-crud-spring-boot-auth";
    private final UserPersistenceEntityMapper userPersistenceEntityMapper;

    @Override
    public String generateToken(UserDomainEntity userDomainEntity) throws ErrorCreatingJWTException {
        UserPersistenceEntity userPersistenceEntity = this.userPersistenceEntityMapper.toPersistence(userDomainEntity);

        try {
            Algorithm algorithm = Algorithm.HMAC256(JwtService.JWT_SECRET);

            return JWT.create().withIssuer(JwtService.JWT_ISSUER) // Nome do EMISSOR
                      .withSubject(userPersistenceEntity.getEmail()) // É o "sub" do JWT, a quem o Token PERTENCE
                      .withClaim("name", userPersistenceEntity.getName()).withExpiresAt(getExpirationDate(24, "-03:00"))
                      .sign(algorithm); // "-03:00" = Brasil

        } catch (JWTCreationException exception) {
            throw new ErrorCreatingJWTException();
        }
    }

    @Override
    public String validateToken(String token) throws ErrorJWTVerificationException {
        try {
            Algorithm algorithm = Algorithm.HMAC256(JwtService.JWT_SECRET);

            return JWT.require(algorithm).withIssuer(JwtService.JWT_ISSUER).build().verify(token).getSubject();
        } catch (JWTVerificationException exception) {
            throw new ErrorJWTVerificationException();
        }
    }

    @Override
    public Instant getExpirationDate(Integer plusHours, String zoneOffSetId) {
        return LocalDateTime.now().plusHours(plusHours).toInstant(ZoneOffset.of(zoneOffSetId));
    }
}