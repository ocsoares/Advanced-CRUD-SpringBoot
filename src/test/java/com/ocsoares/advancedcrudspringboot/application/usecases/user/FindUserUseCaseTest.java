package com.ocsoares.advancedcrudspringboot.application.usecases.user;

import com.ocsoares.advancedcrudspringboot.application.gateways.user.IUserRepositoryGateway;
import com.ocsoares.advancedcrudspringboot.application.usecases.mapper.UserUseCaseMapper;
import com.ocsoares.advancedcrudspringboot.application.usecases.response.UserResponse;
import com.ocsoares.advancedcrudspringboot.domain.entity.UserDomainEntity;
import com.ocsoares.advancedcrudspringboot.domain.exceptions.user.InvalidUserByIdException;
import com.ocsoares.advancedcrudspringboot.infrastructure.persistence.entity.UserPersistenceEntity;
import com.ocsoares.advancedcrudspringboot.utils.TestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.mockito.Mockito.*;

class FindUserUseCaseTest {
    private final UserPersistenceEntity testUser = TestUtils.createUserWithId();

    @Mock
    private IUserRepositoryGateway userRepositoryGateway;

    @Mock
    private UserUseCaseMapper userUseCaseMapper;

    @InjectMocks
    private FindUserUseCase findUserUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("It SHOULD NOT be possible to find a user if it does not exists by ID")
    void execute_Fail_WhenUserDoesNotExistsById() {
        when(this.userRepositoryGateway.findUserById(testUser.getId())).thenReturn(Optional.empty());

        InvalidUserByIdException useCaseException = Assertions.assertThrows(InvalidUserByIdException.class,
                () -> this.findUserUseCase.execute(testUser.getId())
        );

        verify(this.userRepositoryGateway, times(1)).findUserById(testUser.getId());
        Assertions.assertEquals(InvalidUserByIdException.EXCEPTION_MESSAGE, useCaseException.getMessage());
    }

    @Test
    @DisplayName("It should be possible to find a user")
    void execute() throws InvalidUserByIdException {
        UserDomainEntity testUserDomain = TestUtils.toDomain(testUser);
        when(this.userRepositoryGateway.findUserById(testUser.getId())).thenReturn(Optional.of(testUserDomain));

        UserResponse userResponse = TestUtils.toResponse(testUserDomain);
        when(this.userUseCaseMapper.toResponse(testUserDomain)).thenReturn(userResponse);

        UserResponse userFound = this.findUserUseCase.execute(testUser.getId());

        verify(this.userRepositoryGateway, times(1)).findUserById(testUser.getId());
        verify(this.userUseCaseMapper, times(1)).toResponse(testUserDomain);
        Assertions.assertEquals(userFound, userResponse);
    }
}