package com.ocsoares.advancedcrudspringboot.application.usecases.user;

import com.ocsoares.advancedcrudspringboot.application.gateways.user.IUserRepositoryGateway;
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

class DeleteUserUseCaseTest {
    private final UserPersistenceEntity testUser = TestUtils.createUserWithId();
    @Mock
    private IUserRepositoryGateway userRepositoryGateway;
    @InjectMocks
    private DeleteUserUseCase deleteUserUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("It SHOULD NOT be possible to delete a user if it does not exists by ID")
    void execute_Fail_WhenUserDoesNotExistsById() {
        InvalidUserByIdException useCaseException = Assertions.assertThrows(InvalidUserByIdException.class,
                () -> this.deleteUserUseCase.execute(testUser.getId())
        );

        Assertions.assertEquals(InvalidUserByIdException.EXCEPTION_MESSAGE, useCaseException.getMessage());
    }

    @Test
    @DisplayName("It should be possible to delete a user")
    void execute() throws InvalidUserByIdException {
        var testUserDomain = new UserDomainEntity(testUser.getName(), testUser.getEmail(), testUser.getPassword());
        when(this.userRepositoryGateway.findUserById(testUser.getId())).thenReturn(Optional.of(testUserDomain));

        Void deletedUser = this.deleteUserUseCase.execute(testUser.getId());

        verify(this.userRepositoryGateway, times(1)).findUserById(testUser.getId());
        verify(this.userRepositoryGateway, times(1)).deleteUserById(testUser.getId());
        Assertions.assertNull(deletedUser);
    }
}