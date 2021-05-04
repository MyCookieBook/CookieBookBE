package de.cookiebook.restservice.user;

import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserControllerTest {
    private final UserRepository userRepository = mock(UserRepository.class);
    private final UserController userController = new UserController(userRepository);

    @Test
    void registerUser_200() {
//        Given
        User user = new User("test", "test");
//        when
        when(userController.registerUser(user)).thenReturn(user.getId());
//        then
        long id = userController.registerUser(user);
        assertEquals(0, id);
    }

    @Test
    void registerUser_400() {
//        Given
        User user = new User("test", "test");
//        when
        when(userController.registerUser(user)).thenReturn((long) 400);
//        then
        long id = userController.registerUser(user);
        assertEquals(400, id);

        /* test fails, because return statement of the method is the user id = 0*/
    }

    // !
    @Test
    void loginUser_200() {
//        Given
        User user = new User("test", "test");
//        when
        when(userController.loginUser(user)).thenReturn(user.getId());
//        then
        long id = userController.registerUser(user);
        assertEquals(0, id);
    }

    // !
    @Test
    void loginUser_400() {
//        Given
        User user = new User("test", "test");
//        when
        when(userController.loginUser(user)).thenReturn((long) 400);
//        then
        long id = userController.registerUser(user);
        assertEquals(400, id);
    }

    // !
    @Test
    void logUserOut() {
//        Given
        User user = new User("test", "test");
//        when
        when(userController.logUserOut(user)).thenReturn(Status.SUCCESS);
//        then
        Status status = userController.logUserOut(user);
        assertEquals(Status.SUCCESS, status);
    }

    @Test
    void getUsers_Successful() {
//        Given
        List<User> userList = Collections.singletonList(new User("test", "test"));
//        when
        when(userController.getUsers()).thenReturn(userList);
//        then
        List<User> users = userController.getUsers();
        assertEquals(userList, users);
    }
}