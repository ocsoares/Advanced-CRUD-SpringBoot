package com.ocsoares.advancedcrudspringboot.infrastructure.controllers.user;

import com.ocsoares.advancedcrudspringboot.application.usecases.response.UserResponse;
import com.ocsoares.advancedcrudspringboot.application.usecases.user.FindAllUsersUseCase;
import com.ocsoares.advancedcrudspringboot.infrastructure.controllers.interfaces.IControllerWithoutArgument;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
    @Operation(summary = "Find all users", tags = "User")
    @ApiResponse(responseCode = "200")
    @ApiResponse(responseCode = "403", content = @Content)
    @ApiResponse(responseCode = "500")
    @GetMapping("user")
    @ResponseStatus(HttpStatus.OK)
    public List<UserResponse> handle() {
        return this.findAllUsersUseCase.execute();
    }
}
