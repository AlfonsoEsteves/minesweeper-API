package com.example.minesweeper.service;

import com.example.minesweeper.gamelogic.User;
import com.example.minesweeper.gamelogic.exceptions.InvalidGameSettingsException;
import com.example.minesweeper.repository.UserRepository;
import com.example.minesweeper.service.exception.UserAlreadyExistsException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

public class UserServiceTest {

    private static final String userName = "testPlayer";
    private static final String nonExistingUserName = "nonExisting";
    private static final String newUserName = "newUser";
    private static final String password = "1234";

    private static UserRepository userRepositoryMock;

    @BeforeEach
    public void initialize() {
        userRepositoryMock = Mockito.mock(UserRepository.class);
        User user = new User();
        user.setName(userName);
        Mockito.when(userRepositoryMock.findById(userName)).thenReturn(Optional.of(user));
    }

    @Test
    public void testLoadUser_userFound() {
        UserService userService = new UserService(userRepositoryMock);
        User user = userService.loadUser(userName);
        Assertions.assertEquals(userName, user.getName());
    }

    @Test
    public void testLoadUser_nonExistingUser() {
        UserService userService = new UserService(userRepositoryMock);
        User user = userService.loadUser(nonExistingUserName);
        Assertions.assertNull(user);
    }

    @Test
    public void testCreateUser_newUser() {
        UserService userService = new UserService(userRepositoryMock);
        User user = userService.createUser(newUserName, password);
        Assertions.assertEquals(newUserName, user.getName());
    }

    @Test
    public void testCreateUser_existingUser() {
        UserService userService = new UserService(userRepositoryMock);
        Assertions.assertThrows(UserAlreadyExistsException.class, () -> {
            User user = userService.createUser(userName, password);
        });

    }

}
