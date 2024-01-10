package com.ocsoares.advancedcrudspringboot.infrastructure.controllers.user;

import com.ocsoares.advancedcrudspringboot.application.gateways.security.IAuthServiceGateway;
import com.ocsoares.advancedcrudspringboot.infrastructure.controllers.interfaces.IControllerWithArgument;
import com.ocsoares.advancedcrudspringboot.infrastructure.controllers.user.dtos.LoginDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LoginUserController implements IControllerWithArgument<String, LoginDTO, Exception> {
    private final IAuthServiceGateway authServiceGateway;

    @PostMapping("auth/login")
    @ResponseStatus(HttpStatus.OK)
    @Override
    public String handle(@RequestBody @Valid LoginDTO loginDTO) throws Exception {
        return this.authServiceGateway.login(loginDTO.email(), loginDTO.password());
    }
}
