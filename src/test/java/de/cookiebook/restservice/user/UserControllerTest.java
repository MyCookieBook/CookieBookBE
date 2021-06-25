package de.cookiebook.restservice.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserControllerTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private HttpServletResponse httpServletResponse;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testRegisterUser_SUCCESS() {
        // Arrange
        User stubUser = new User("user2@email.com", "user2_password");
        stubUser.setId(1);
        List<User> listOfStubUser = Collections.singletonList(new User("user1@email.com", "user1_password"));
        when(userRepository.findAll()).thenReturn(listOfStubUser);
        when(userRepository.save(any())).thenReturn(stubUser);
        // Act
        long userId = userController.registerUser(stubUser);
        // Assert
        assertEquals(1, userId);
    }

    @Test
    public void testRegisterUser_USER_ALREADY_EXISTS() {
        // Arrange
        User stubUser = new User("user2@email.com", "user2_password");
        stubUser.setId(1);
        List<User> listOfStubUser = Collections.singletonList(stubUser);
        when(userRepository.findAll()).thenReturn(listOfStubUser);
        when(userRepository.save(any())).thenReturn(stubUser);
        // Act
        long statusCode = userController.registerUser(stubUser);
        // Assert
        assertEquals(41, statusCode);
    }

    @Test
    public void testRegisterUser_EXCEPTION() {
        // Arrange
        User stubUser = new User("user2@email.com", "user2_password");
        stubUser.setId(1);
        when(userRepository.findAll()).thenThrow(new IllegalArgumentException("test exception"));
        // Act
        long statusCode = userController.registerUser(stubUser);
        // Assert
        assertEquals(40, statusCode);
    }

    @Test
    public void testLoginUser_SUCCESS() {
        // Arrange
        User stubUser = new User("user1@email.com", "user1_password");
        stubUser.setId(1);
        List<User> listOfStubUser = Collections.singletonList(stubUser);
        when(userRepository.findAll()).thenReturn(listOfStubUser);
        when(userRepository.save(any())).thenReturn(stubUser);
        // Act
        long userId = userController.loginUser("user1@email.com", "user1_password");
        // Assert
        assertEquals(1, userId);
    }

    @Test
    public void testLoginUser_USER_INVALID() {
        // Arrange
        User stubUser = new User("user1@email.com", "user1_password");
        stubUser.setId(1);
        List<User> listOfStubUser = Collections.singletonList(stubUser);
        when(userRepository.findAll()).thenReturn(listOfStubUser);
        when(userRepository.save(any())).thenReturn(stubUser);
        // Act
        long statusCode = userController.loginUser("user2@email.com", "user2_password");
        // Assert
        assertEquals(40, statusCode);
    }

    @Test
    public void testLoginUser_EXCEPTION() {
        // Arrange
        when(userRepository.findAll()).thenThrow(new IllegalArgumentException("test exception"));
        // Act
        long statusCode = userController.loginUser("user1@email.com", "user1_password");
        // Assert
        assertEquals(40, statusCode);
    }

    @Test
    public void testLogoutUser_SUCCESS() {
        // Arrange
        User stubUser = new User("user1@email.com", "user1_password");
        stubUser.setId(1);
        List<User> listOfStubUser = Collections.singletonList(stubUser);
        when(userRepository.findAll()).thenReturn(listOfStubUser);
        when(userRepository.save(any())).thenReturn(stubUser);
        // Act
        long statusCode = userController.logUserOut(1);
        // Assert
        assertEquals(20, statusCode);
    }

    @Test
    public void testLogoutUser_FAILURE() {
        // Arrange
        User stubUser = new User("user1@email.com", "user1_password");
        stubUser.setId(1);
        List<User> listOfStubUser = Collections.singletonList(stubUser);
        when(userRepository.findAll()).thenReturn(listOfStubUser);
        when(userRepository.save(any())).thenReturn(stubUser);
        // Act
        long statusCode = userController.logUserOut(2);
        // Assert
        assertEquals(40, statusCode);
    }

    @Test
    public void testGetUsers(){
        // Arrange
        User stubUser = new User("user1@email.com", "user1_password");
        stubUser.setId(1);
        List<User> listOfStubUser = Collections.singletonList(stubUser);
        when(userRepository.findAll()).thenReturn(listOfStubUser);
        // Act
        List<User> users = userController.getUsers();
        // Assert
        assertNotNull(users);
        assertEquals(1, users.size());
        assertNotNull(users.get(0));
        assertEquals(1, users.get(0).getId());
        assertEquals("user1@email.com", users.get(0).getEmail());
        assertEquals("user1_password", users.get(0).getPassword());
    }

    @Test
    public void testGetUser_SUCCESS(){
        // Arrange
        User stubUser = new User("user1@email.com", "user1_password");
        stubUser.setId(1);
        stubUser.setUsername("user1");
        stubUser.setDuration(new Timestamp(System.currentTimeMillis() + 10000000));
        List<User> listOfStubUser = Collections.singletonList(stubUser);
        when(userRepository.findAll()).thenReturn(listOfStubUser);
        // Act
        String[] users = userController.getUser(1);
        // Assert
        assertNotNull(users);
        assertEquals(2, users.length);
        assertEquals("user1@email.com", users[0]);
        assertEquals("user1", users[1]);
    }

    @Test
    public void testGetUser_INVALID_USER(){
        // Arrange
        User stubUser = new User("user2@email.com", "user2_password");
        stubUser.setId(1);
        stubUser.setUsername("user2");
        stubUser.setDuration(new Timestamp(System.currentTimeMillis() + 10000000));
        List<User> listOfStubUser = Collections.singletonList(new User("user1@email.com", "user1_password"));
        when(userRepository.findAll()).thenReturn(listOfStubUser);
        // Act
        String[] users = userController.getUser(2);
        // Assert
        assertNull(users);
    }

    @Test
    public void testGetUser_INVALID_DURATION(){
        // Arrange
        User stubUser = new User("user1@email.com", "user1_password");
        stubUser.setId(1);
        stubUser.setUsername("user1");
        stubUser.setDuration(new Timestamp(System.currentTimeMillis() - 10000000));
        List<User> listOfStubUser = Collections.singletonList(stubUser);
        when(userRepository.findAll()).thenReturn(listOfStubUser);
        // Act
        String[] users = userController.getUser(1);
        // Assert
        assertNull(users);
    }

    @Test
    public void testGetUser_EXCEPTION(){
        // Arrange
        User stubUser = new User("user2@email.com", "user2_password");
        stubUser.setId(1);
        stubUser.setUsername("user2");
        stubUser.setDuration(new Timestamp(System.currentTimeMillis() + 10000000));
        when(userRepository.findAll()).thenThrow(new IllegalArgumentException("test exception"));
        // Act
        String[] users = userController.getUser(1);
        // Assert
        assertNull(users);
    }

    @Test
    public void testEditProfile_SUCCESS(){
        // Arrange
        User stubUser = new User("user@email.com", "password");
        stubUser.setId(1);
        stubUser.setUsername("user");
        stubUser.setDuration(new Timestamp(System.currentTimeMillis() + 10000000));
        User newStubUser = new User(null, null);
        when(userRepository.findById(anyInt())).thenReturn(stubUser);
        when(userRepository.save(any())).thenReturn(stubUser);
        // Act
        long statusCode = userController.editProfile(newStubUser);
        // Assert
        assertEquals(20, statusCode);
    }

    @Test
    public void testEditProfile_INVALID_DURATION(){
        // Arrange
        User stubUser = new User("user@email.com", "password");
        stubUser.setId(1);
        stubUser.setUsername("user");
        stubUser.setDuration(new Timestamp(System.currentTimeMillis() - 10000000));
        User newStubUser = new User(null, null);
        when(userRepository.findById(anyInt())).thenReturn(stubUser);
        // Act
        long statusCode = userController.editProfile(newStubUser);
        // Assert
        assertEquals(40, statusCode);
    }

    @Test
    public void testEditProfile_EXCEPTION(){
        // Arrange
        User stubUser = new User("user@email.com", "password");
        stubUser.setId(1);
        stubUser.setUsername("user");
        stubUser.setDuration(new Timestamp(System.currentTimeMillis() - 10000000));
        User newStubUser = new User(null, null);
        when(userRepository.findById(anyInt())).thenThrow(new IllegalArgumentException("test exception"));
        // Act
        long statusCode = userController.editProfile(newStubUser);
        // Assert
        assertEquals(40, statusCode);
    }

    @Test
    public void testChangePassword_SUCCESS(){
        // Arrange
        User stubUser = new User("user1@email.com", "user1_password");
        stubUser.setId(1);
        stubUser.setDuration(new Timestamp(System.currentTimeMillis() + 10000000));
        List<User> listOfStubUser = Collections.singletonList(stubUser);
        when(userRepository.findAll()).thenReturn(listOfStubUser);
        when(userRepository.save(any())).thenReturn(stubUser);
        // Act
        long statusCode = userController.changePassword(stubUser, "new_password", httpServletResponse);
        // Assert
        assertEquals(20, statusCode);
    }

    @Test
    public void testChangePassword_INVALID_USER(){
        // Arrange
        User stubUser = new User("user2@email.com", "user2_password");
        stubUser.setId(2);
        stubUser.setDuration(new Timestamp(System.currentTimeMillis() + 10000000));
        List<User> listOfStubUser = Collections.singletonList(new User("user1@email.com", "user1_password"));
        when(userRepository.findAll()).thenReturn(listOfStubUser);
        when(userRepository.save(any())).thenReturn(stubUser);
        // Act
        long statusCode = userController.changePassword(stubUser, "new_password", httpServletResponse);
        // Assert
        assertEquals(40, statusCode);
    }

    @Test
    public void testChangePassword_INVALID_DURATION(){
        // Arrange
        User stubUser = new User("user1@email.com", "user1_password");
        stubUser.setId(1);
        stubUser.setDuration(new Timestamp(System.currentTimeMillis() - 10000000));
        List<User> listOfStubUser = Collections.singletonList(stubUser);
        when(userRepository.findAll()).thenReturn(listOfStubUser);
        when(userRepository.save(any())).thenReturn(stubUser);
        // Act
        long statusCode = userController.changePassword(stubUser, "new_password", httpServletResponse);
        // Assert
        assertEquals(40, statusCode);
    }

    @Test
    public void testChangePassword_Exception(){
        // Arrange
        User stubUser = new User("user1@email.com", "user1_password");
        stubUser.setId(1);
        stubUser.setDuration(new Timestamp(System.currentTimeMillis() - 10000000));
        when(userRepository.findAll()).thenThrow(new IllegalArgumentException("test exception"));
        // Act
        long statusCode = userController.changePassword(stubUser, "new_password", httpServletResponse);
        // Assert
        assertEquals(40, statusCode);
    }

}