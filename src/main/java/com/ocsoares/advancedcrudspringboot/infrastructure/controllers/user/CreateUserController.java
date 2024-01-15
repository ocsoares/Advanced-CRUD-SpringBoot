package com.ocsoares.advancedcrudspringboot.infrastructure.controllers.user;

import com.ocsoares.advancedcrudspringboot.application.usecases.response.UserResponse;
import com.ocsoares.advancedcrudspringboot.application.usecases.user.CreateUserUseCase;
import com.ocsoares.advancedcrudspringboot.domain.entity.UserDomainEntity;
import com.ocsoares.advancedcrudspringboot.domain.exceptions.user.UserAlreadyExistsByEmailException;
import com.ocsoares.advancedcrudspringboot.infrastructure.controllers.interfaces.IControllerWithArgument;
import com.ocsoares.advancedcrudspringboot.infrastructure.controllers.user.dtos.UserDTO;
import com.ocsoares.advancedcrudspringboot.infrastructure.controllers.user.mappers.UserControllerMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CreateUserController implements IControllerWithArgument<UserResponse, UserDTO, Exception> {
    private final CreateUserUseCase createUserUseCase;
    private final UserControllerMapper userControllerMapper;

    @Override
    @PostMapping("auth/register")
    @ResponseStatus(HttpStatus.CREATED)
    @Transactional
    public UserResponse handle(@RequestBody @Valid UserDTO userDTO) throws UserAlreadyExistsByEmailException {
        // Como o "CreateUserUseCase" usa apenas o Objeto de DOMÍNIO usado nas REGRAS de NEGÓCIO da
        // Aplicação, no caso o "UserDomainEntity", PRECISA CONVERTER esse "createUserDTO" para o
        // DOMÍNIO!!!
        UserDomainEntity createUserDomain = this.userControllerMapper.toDomain(userDTO);

        return this.createUserUseCase.execute(createUserDomain);
    }
}
