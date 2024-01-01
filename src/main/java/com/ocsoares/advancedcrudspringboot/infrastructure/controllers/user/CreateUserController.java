package com.ocsoares.advancedcrudspringboot.infrastructure.controllers.user;

import com.ocsoares.advancedcrudspringboot.application.usecases.user.CreateUserUseCase;
import com.ocsoares.advancedcrudspringboot.domain.entity.UserDomainEntity;
import com.ocsoares.advancedcrudspringboot.infrastructure.controllers.interfaces.IControllerWithArgument;
import com.ocsoares.advancedcrudspringboot.infrastructure.controllers.user.dtos.CreateUserDTO;
import com.ocsoares.advancedcrudspringboot.infrastructure.controllers.user.mapper.UserDTOMapper;
import com.ocsoares.advancedcrudspringboot.infrastructure.controllers.user.response.CreateUserResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CreateUserController implements IControllerWithArgument<CreateUserResponse, CreateUserDTO> {
    private final CreateUserUseCase createUserUseCase;
    private final UserDTOMapper userDTOMapper;

    public CreateUserController(CreateUserUseCase createUserUseCase, UserDTOMapper userDTOMapper) {
        this.createUserUseCase = createUserUseCase;
        this.userDTOMapper = userDTOMapper;
    }

    @PostMapping("user")
    public CreateUserResponse handle(CreateUserDTO createUserDTO) {
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
