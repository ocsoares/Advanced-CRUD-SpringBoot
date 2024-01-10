package com.ocsoares.advancedcrudspringboot.application.gateways.security;

import com.ocsoares.advancedcrudspringboot.domain.exceptions.security.ErrorCreatingJWTException;

public interface IAuthServiceGateway {
    String login(String email, String password) throws ErrorCreatingJWTException;
}
