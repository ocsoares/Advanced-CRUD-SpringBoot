package com.ocsoares.advancedcrudspringboot.infrastructure.controllers.user;

import com.ocsoares.advancedcrudspringboot.application.usecases.response.UserResponse;
import com.ocsoares.advancedcrudspringboot.application.usecases.user.FindAllUsersUseCase;
import com.ocsoares.advancedcrudspringboot.infrastructure.controllers.interfaces.IControllerWithoutArgument;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FindAllUsersController implements IControllerWithoutArgument<List<UserResponse>> {
    private final FindAllUsersUseCase findAllUsersUseCase;

    @Override
    @GetMapping("user")
    @ResponseStatus(HttpStatus.OK)
    public List<UserResponse> handle() {
        return this.findAllUsersUseCase.execute();
    }
}
