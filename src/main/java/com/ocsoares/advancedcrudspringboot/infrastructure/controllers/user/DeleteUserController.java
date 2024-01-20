package com.ocsoares.advancedcrudspringboot.infrastructure.controllers.user;

import com.ocsoares.advancedcrudspringboot.application.usecases.user.DeleteUserUseCase;
import com.ocsoares.advancedcrudspringboot.infrastructure.controllers.interfaces.IControllerWithArgument;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class DeleteUserController implements IControllerWithArgument<Void, UUID, Exception> {
    private final DeleteUserUseCase deleteUserUseCase;

    @Operation(summary = "Delete a user", tags = "User")
    @ApiResponse(responseCode = "204")
    @ApiResponse(responseCode = "400")
    @ApiResponse(responseCode = "403")
    @ApiResponse(responseCode = "500")
    @DeleteMapping("user/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Override
    public Void handle(@PathVariable(value = "id") UUID id) throws Exception {
        this.deleteUserUseCase.execute(id);

        return null;
    }
}
