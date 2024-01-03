package com.ocsoares.advancedcrudspringboot.infrastructure.controllers.user;

import com.ocsoares.advancedcrudspringboot.application.usecases.user.FindAllUsersUseCase;
import com.ocsoares.advancedcrudspringboot.domain.entity.UserDomainEntity;
import com.ocsoares.advancedcrudspringboot.infrastructure.controllers.interfaces.IControllerWithoutArgument;
import com.ocsoares.advancedcrudspringboot.infrastructure.controllers.user.mapper.UserControllerMapper;
import com.ocsoares.advancedcrudspringboot.infrastructure.controllers.user.response.CreateUserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FindAllUsersController implements IControllerWithoutArgument<List<CreateUserResponse>> {
    private final FindAllUsersUseCase findAllUsersUseCase;
    private final UserControllerMapper userControllerMapper;

    @Override
    @GetMapping("user")
    @ResponseStatus(HttpStatus.OK)
    public List<CreateUserResponse> handle() {
        List<UserDomainEntity> allUsersFound = this.findAllUsersUseCase.execute();

        return this.userControllerMapper.toResponseList(allUsersFound);
    }
}
