package com.ocsoares.advancedcrudspringboot.infrastructure.controllers.user;

import com.ocsoares.advancedcrudspringboot.application.gateways.security.ITokenServiceGateway;
import com.ocsoares.advancedcrudspringboot.application.gateways.user.IUserRepositoryGateway;
import com.ocsoares.advancedcrudspringboot.domain.entity.UserDomainEntity;
import com.ocsoares.advancedcrudspringboot.domain.exceptions.security.ErrorCreatingJWTException;
import com.ocsoares.advancedcrudspringboot.domain.exceptions.user.InvalidUserByEmailException;
import com.ocsoares.advancedcrudspringboot.domain.exceptions.user.InvalidUserByIdException;
import com.ocsoares.advancedcrudspringboot.utils.TestUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.UUID;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc()
@ActiveProfiles("test")
class FindUserControllerTest {
    private static final String URI_WITH_INVALID_USER_ID = "/user/" + UUID.randomUUID();
    private static final UserDomainEntity userDTO = TestUtils.createUser();
    private String token;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private IUserRepositoryGateway userRepositoryGateway;

    @Autowired
    private ITokenServiceGateway tokenServiceGateway;

    @BeforeEach
    void setUp() throws ErrorCreatingJWTException, InvalidUserByEmailException {
        this.token = TestUtils.generateToken(this.userRepositoryGateway, this.tokenServiceGateway);
    }

    @AfterEach
    void down() {
        this.userRepositoryGateway.deleteAll();
    }

    @Test
    @DisplayName("It SHOULD NOT be possible to find a user if forbidden request")
    void handle_Fail_WhenForbiddenRequest() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get(FindUserControllerTest.URI_WITH_INVALID_USER_ID))
                .andExpect(MockMvcResultMatchers.status().isForbidden())
                .andExpect(MockMvcResultMatchers.content().string(""));
    }

    @Test
    @DisplayName("It SHOULD NOT be possible to find a user if it does not exists by id")
    void handle_Fail_WhenUserDoesNotExistsById() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get(FindUserControllerTest.URI_WITH_INVALID_USER_ID)
                        .header("Authorization", "Bearer " + this.token))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$.message").value(InvalidUserByIdException.EXCEPTION_MESSAGE));
    }

    @Test
    @DisplayName("It should be possible to find a user")
    void handle() throws Exception {
        String userId = TestUtils.createUserAndGetId(this.userRepositoryGateway, FindUserControllerTest.userDTO);
        String expectedUserResponse = TestUtils.expectedUserResponse();

        this.mockMvc.perform(
                        MockMvcRequestBuilders.get("/user/" + userId).header("Authorization", "Bearer " + this.token))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(expectedUserResponse));
    }
}