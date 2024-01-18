package com.ocsoares.advancedcrudspringboot.infrastructure.controllers.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ocsoares.advancedcrudspringboot.application.gateways.security.ITokenServiceGateway;
import com.ocsoares.advancedcrudspringboot.application.gateways.user.IUserRepositoryGateway;
import com.ocsoares.advancedcrudspringboot.application.usecases.response.UserResponse;
import com.ocsoares.advancedcrudspringboot.domain.entity.UserDomainEntity;
import com.ocsoares.advancedcrudspringboot.utils.TestUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc()
@ActiveProfiles("test")
class FindAllUsersControllerTest {

    private static final String URI = "/user";
    private static final UserDomainEntity userDTO = TestUtils.createUser();
    private String token;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private IUserRepositoryGateway userRepositoryGateway;

    @Autowired
    private ITokenServiceGateway tokenServiceGateway;

    @AfterEach
    void down() {
        this.userRepositoryGateway.deleteAll();
    }

    @Test
    @DisplayName("It SHOULD NOT be possible to find all users if forbidden request")
    void handle_Fail_WhenForbiddenRequest() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get(FindAllUsersControllerTest.URI))
                .andExpect(MockMvcResultMatchers.status().isForbidden())
                .andExpect(MockMvcResultMatchers.content().string(""));
    }

    @Test
    @DisplayName("It should be possible to find all users")
    void handle() throws Exception {
        // Tive que fazer assim porque o Método "TestUtils.generateToken" precisa CRIAR um Usuário, então esse meu
        // Teste iria dar ERRO, porque ia ter mais Usuários que o esperado!!!
        UserDomainEntity createdUser = this.userRepositoryGateway.createUser(userDTO);
        this.token = TestUtils.generateTokenWithOwnUser(this.userRepositoryGateway, this.tokenServiceGateway,
                createdUser
        );

        var userDTOTwo = new UserDomainEntity("Fernando", "fernando@gmail.com", "fernando123");
        UserDomainEntity createdUserTwo = this.userRepositoryGateway.createUser(userDTOTwo);

        List<UserResponse> userResponseList = List.of(new UserResponse(createdUser.name(), createdUser.email()),
                new UserResponse(createdUserTwo.name(), createdUserTwo.email())
        );

        String JSONUserResponseList = this.objectMapper.writeValueAsString(userResponseList);

        this.mockMvc.perform(MockMvcRequestBuilders.get(FindAllUsersControllerTest.URI)
                        .header("Authorization", "Bearer " + this.token))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(JSONUserResponseList));
    }
}