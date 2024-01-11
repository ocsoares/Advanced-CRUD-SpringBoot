package com.ocsoares.advancedcrudspringboot.infrastructure.controllers.user;

import com.ocsoares.advancedcrudspringboot.application.gateways.security.IAuthServiceGateway;
import com.ocsoares.advancedcrudspringboot.infrastructure.controllers.interfaces.IControllerWithArgument;
import com.ocsoares.advancedcrudspringboot.infrastructure.controllers.user.dtos.LoginDTO;
import com.ocsoares.advancedcrudspringboot.infrastructure.controllers.user.response.TokenResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LoginUserController implements IControllerWithArgument<TokenResponse, LoginDTO, Exception> {
    private final IAuthServiceGateway authServiceGateway;

    @PostMapping("auth/login")
    @Override
    public TokenResponse handle(@RequestBody @Valid LoginDTO loginDTO) throws Exception {
        String token = this.authServiceGateway.login(loginDTO.email(), loginDTO.password());

        return new TokenResponse(token);
    }
}
