package com.ocsoares.advancedcrudspringboot.application.usecases.user;

import com.ocsoares.advancedcrudspringboot.application.gateways.user.IUserRepositoryGateway;
import com.ocsoares.advancedcrudspringboot.application.usecases.mapper.UserUseCaseMapper;
import com.ocsoares.advancedcrudspringboot.application.usecases.response.UserResponse;
import com.ocsoares.advancedcrudspringboot.domain.entity.UserDomainEntity;
import com.ocsoares.advancedcrudspringboot.utils.TestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.mockito.Mockito.*;

class FindAllUsersUseCaseTest {
    private final UserDomainEntity testUser = TestUtils.createUser();

    @Mock
    private IUserRepositoryGateway userRepositoryGateway;
    @Mock
    private UserUseCaseMapper userUseCaseMapper;
    @InjectMocks
    private FindAllUsersUseCase findAllUsersUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("It should be possible to find all users")
    void execute() {
        List<UserDomainEntity> testUserList = List.of(testUser, testUser, testUser);
        when(this.userRepositoryGateway.findAllUsers()).thenReturn(testUserList);

        List<UserResponse> testUserListResponse = testUserList.stream()
                .map(user -> new UserResponse(user.name(), user.email())).toList();
        when(this.userUseCaseMapper.toResponseList(testUserList)).thenReturn(testUserListResponse);

        List<UserResponse> allUsersFound = this.findAllUsersUseCase.execute();

        verify(this.userRepositoryGateway, times(1)).findAllUsers();
        Assertions.assertEquals(allUsersFound, testUserListResponse);
    }

    @Test
    @DisplayName("It should be possible to find all users when the user list is empty")
    void execute_WhenEmptyUserList() {
        List<UserDomainEntity> testUserList = List.of();
        when(this.userRepositoryGateway.findAllUsers()).thenReturn(testUserList);

        List<UserResponse> testUserListResponse = testUserList.stream()
                .map(user -> new UserResponse(user.name(), user.email())).toList();
        when(this.userUseCaseMapper.toResponseList(testUserList)).thenReturn(testUserListResponse);

        List<UserResponse> allUsersFound = this.findAllUsersUseCase.execute();

        verify(this.userRepositoryGateway, times(1)).findAllUsers();
        Assertions.assertEquals(allUsersFound, testUserListResponse);
    }
}