package com.ocsoares.advancedcrudspringboot.infrastructure.controllers.user;

import com.ocsoares.advancedcrudspringboot.application.usecases.user.CreateUserUseCase;
import com.ocsoares.advancedcrudspringboot.domain.entity.UserDomainEntity;
import com.ocsoares.advancedcrudspringboot.domain.exceptions.user.UserAlreadyExistsByEmailException;
import com.ocsoares.advancedcrudspringboot.infrastructure.controllers.interfaces.IControllerWithArgument;
import com.ocsoares.advancedcrudspringboot.infrastructure.controllers.user.dtos.CreateUserDTO;
import com.ocsoares.advancedcrudspringboot.infrastructure.controllers.user.mapper.UserControllerMapper;
import com.ocsoares.advancedcrudspringboot.infrastructure.controllers.user.response.UserResponse;
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
public class CreateUserController implements IControllerWithArgument<UserResponse, CreateUserDTO, Exception> {
    private final CreateUserUseCase createUserUseCase;
    private final UserControllerMapper userControllerMapper;

    @Override
    @PostMapping("user")
    @ResponseStatus(HttpStatus.CREATED)
    @Transactional
    public UserResponse handle(@RequestBody @Valid CreateUserDTO createUserDTO) throws
            UserAlreadyExistsByEmailException {
        // Como o "CreateUserUseCase" usa apenas o Objeto de DOMÍNIO usado nas REGRAS de NEGÓCIO da
        // Aplicação, no caso o "UserDomainEntity", PRECISA CONVERTER esse "createUserDTO" para o
        // DOMÍNIO!!!
        UserDomainEntity createUserDomain = this.userControllerMapper.toDomain(createUserDTO);

        UserDomainEntity createdUser = this.createUserUseCase.execute(createUserDomain);

        // O "createdUser" é um "UserDomainEntity", então PRECISA CONVERTER ele para o Retorno do Método,
        // que é "CreateUserResponse" !!!
        return this.userControllerMapper.toResponse(createdUser);
    }
}
