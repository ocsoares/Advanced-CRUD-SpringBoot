package com.ocsoares.advancedcrudspringboot.infrastructure.controllers.user;

import com.ocsoares.advancedcrudspringboot.application.usecases.user.CreateUserUseCase;
import com.ocsoares.advancedcrudspringboot.domain.entity.UserDomainEntity;
import com.ocsoares.advancedcrudspringboot.infrastructure.controllers.interfaces.IControllerWithArgument;
import com.ocsoares.advancedcrudspringboot.infrastructure.controllers.user.dtos.CreateUserDTO;
import com.ocsoares.advancedcrudspringboot.infrastructure.controllers.user.mapper.UserDTOMapper;
import com.ocsoares.advancedcrudspringboot.infrastructure.controllers.user.response.CreateUserResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CreateUserController implements IControllerWithArgument<CreateUserResponse, CreateUserDTO, Exception> {
    private final CreateUserUseCase createUserUseCase;
    private final UserDTOMapper userDTOMapper;

    @Override
    @PostMapping("user")
    @ResponseStatus(HttpStatus.CREATED)
    @Transactional
    public CreateUserResponse handle(@RequestBody @Valid CreateUserDTO createUserDTO) throws BadRequestException {
        // Como o "CreateUserUseCase" usa apenas o Objeto de DOMÍNIO usado nas REGRAS de NEGÓCIO da
        // Aplicação, no caso o "UserDomainEntity", PRECISA CONVERTER esse "createUserDTO" para o
        // DOMÍNIO!!!
        UserDomainEntity createUserDomain = this.userDTOMapper.toDomain(createUserDTO);

        UserDomainEntity createdUser = this.createUserUseCase.execute(createUserDomain);

        // O "createdUser" é um "UserDomainEntity", então PRECISA CONVERTER ele para o Retorno do Método,
        // que é "CreateUserResponse" !!!
        return this.userDTOMapper.toResponse(createdUser);
    }
}
