package com.ocsoares.advancedcrudspringboot.infrastructure.controllers.user;

import com.ocsoares.advancedcrudspringboot.application.usecases.user.UpdateUserUseCase;
import com.ocsoares.advancedcrudspringboot.domain.entity.UserDomainEntity;
import com.ocsoares.advancedcrudspringboot.infrastructure.controllers.interfaces.IControllerWithTwoArguments;
import com.ocsoares.advancedcrudspringboot.infrastructure.controllers.user.dtos.UserDTO;
import com.ocsoares.advancedcrudspringboot.infrastructure.controllers.user.mapper.UserControllerMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class UpdateUserController implements IControllerWithTwoArguments<Void, UUID, UserDTO, Exception> {
    private final UpdateUserUseCase updateUserUseCase;
    private final UserControllerMapper userControllerMapper;

    @PatchMapping("user/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    @Override
    public Void handle(
            @PathVariable(value = "id") UUID id, @RequestBody @Valid UserDTO userDTO
    ) throws Exception {
        UserDomainEntity updateUserDomain = this.userControllerMapper.toDomain(userDTO);

        this.updateUserUseCase.execute(id, updateUserDomain);

        return null;
    }
}
