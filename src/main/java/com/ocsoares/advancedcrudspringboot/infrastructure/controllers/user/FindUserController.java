package com.ocsoares.advancedcrudspringboot.infrastructure.controllers.user;

import com.ocsoares.advancedcrudspringboot.application.usecases.user.FindUserUseCase;
import com.ocsoares.advancedcrudspringboot.domain.entity.UserDomainEntity;
import com.ocsoares.advancedcrudspringboot.infrastructure.controllers.interfaces.IControllerWithArgument;
import com.ocsoares.advancedcrudspringboot.infrastructure.controllers.user.mapper.UserControllerMapper;
import com.ocsoares.advancedcrudspringboot.infrastructure.controllers.user.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class FindUserController implements IControllerWithArgument<UserResponse, UUID, Exception> {
    private final FindUserUseCase findUserUseCase;
    private final UserControllerMapper userControllerMapper;

    @GetMapping("user/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Override
    public UserResponse handle(@PathVariable(value = "id") UUID id) throws Exception {
        UserDomainEntity foundUser = this.findUserUseCase.execute(id);

        return this.userControllerMapper.toResponse(foundUser);
    }
}
