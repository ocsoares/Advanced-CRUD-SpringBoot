package com.ocsoares.advancedcrudspringboot.infrastructure.controllers.user;

import com.ocsoares.advancedcrudspringboot.application.usecases.mapper.UserUseCaseMapper;
import com.ocsoares.advancedcrudspringboot.application.usecases.response.UserResponse;
import com.ocsoares.advancedcrudspringboot.application.usecases.user.FindUserUseCase;
import com.ocsoares.advancedcrudspringboot.infrastructure.controllers.interfaces.IControllerWithArgument;
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
    private final UserUseCaseMapper userUseCaseMapper;

    @GetMapping("user/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Override
    public UserResponse handle(@PathVariable(value = "id") UUID id) throws Exception {
        return this.findUserUseCase.execute(id);
    }
}
