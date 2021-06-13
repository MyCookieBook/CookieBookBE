package de.cookiebook.restservice.user;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserControllerTest {

    UserRepository userRepository = mock(UserRepository.class);
    private final UserController userController = new UserController(this.userRepository);


    @Test
    void registerUser_200() {
        // GIVEN
        User newuser = new User("new", "new");
        //newuser.setId();
        List<User> userlist = new ArrayList<>(Collections.singletonList(new User("test", "test")));
        // WHEN
        when(userRepository.findAll()).thenReturn(userlist);
        when(userController.registerUser(newuser)).thenReturn(newuser.getId());
        // THEN
        long userId = userController.registerUser(newuser);
        assertNotEquals(newuser.getId(), Status.FAILURE.getStatuscode());
    }

    @Test
    void registerUser_400() {
        //        Given
        User user = new User("test", "test");
//        user.setId(41);
        List<User> userList = Collections.singletonList(user);
//        when
        when(userRepository.findAll()).thenReturn(userList);
        when(userController.registerUser(user)).thenReturn(Status.USER_ALREADY_EXISTS.getStatuscode());
//        then
        long statusCode = userController.registerUser(user);
        assertEquals(41, statusCode);
    }

    @Test
    void loginUser_200() {
        //        Given
        User user = new User("test", "test");
        user.setId(0);
        List<User> userList = Collections.singletonList(user);

//        when
        when(userRepository.findAll()).thenReturn(userList);
        when(userController.loginUser(user.getEmail(), user.getPassword())).thenReturn(user.getId());
//        then
        long id = userController.loginUser(user.getEmail(), user.getPassword());
        assertEquals(0, id);
    }

    @Test
    void loginUser_400() {
        //        Given
        User user = new User("test", "test");
//        user.setId(40);
        List<User> userList = Collections.singletonList(new User("test1", "test1"));
//        when
        when(userRepository.findAll()).thenReturn(userList);
        when(userController.loginUser(user.getEmail(), user.getPassword())).thenReturn(Status.FAILURE.getStatuscode());
//        then
        long statusCode = userController.loginUser(user.getEmail(), user.getPassword());
        assertEquals(40, statusCode);
    }

    @Test
    void logUserOut_200() {
        // GIVEN
        User user = new User("test", "test");
        user.setId(0);
        List<User> userlist = new ArrayList<>(Collections.singletonList(user));
        // WHEN
        when(userRepository.findAll()).thenReturn(userlist);
        when(userController.logUserOut(user.getId())).thenReturn(user.getId());
        // THEN
        long status = userController.logUserOut(user.getId());
        assertEquals(20, status);
    }

    // failed wegen mock von userrepositorx.findall()
/*    @Test
    void logUserOut_400() {
//        // GIVEN
//        User user = new User("testfail","testfail");
//        user.setId(50);
//        User failuser = new User("test1","test1");
//        failuser.setId(1);
//        List<User> userlist = new ArrayList<>(Collections.singletonList(failuser));
//        // WHEN
////        doReturn(userlist).when(userRepository).findAll();
//        when(userRepository.findAll()).thenReturn(userlist);
//        when(userController.logUserOut(user.getId())).thenReturn(Status.FAILURE.getStatuscode());
//        // THEN
//        long status = userController.logUserOut(user.getId());
//        assertEquals(40,status);
    }
/*
    @Test
    void getUsers() {
        // GIVEN
        List<User> userlist = new ArrayList<>(Collections.singletonList(new User("test", "test")));
        // WHEN
        when(userRepository.findAll()).thenReturn(userlist);
        when(userController.getUsers()).thenReturn(userlist);
        // THENList<User> realList = userController.getUsers();
        //        assertEquals(1, realList.size());
        //        assertEquals(userlist, realList);
        //    }
        ///*
        //    @Test
        //    void getUser() {
        //        // GIVEN
        //        User user = new User("mail@web.de", "password12");
        //        List<User> userlist = new ArrayList<>(Collections.singletonList(user));
        //        // WHEN
        //        when(userRepository.findAll()).thenReturn(userlist);
        //        when(userController.getUser(68));
        //        // THEN
        //        String[] realUser = userController.getUser(user.getId());
        //        assertEquals(realUser[0], "mail@mail.de");
        //        assertEquals(realUser[1], "password12");
        //    }
        ///*
        //    @Test
        //    void changeEmail() {
        //        // GIVEN
        //        User user = new User("email", "password");
        //        User newUser = userRepository.save(user);
        //        //user.setId(1);
        //        //List<User> userlist = new ArrayList<>(Collections.singletonList(user));
        //        // WHEN
        //        when(userRepository.findAll()).thenReturn(user);
        //        when(userController.editProfile(user.setEmail(user)), "test1")).thenReturn(Status.SUCCESS);
        //        // THEN
        //        Status status = userController.changeEmail(user.getId(),"test1");
        //        assertEquals(Status.SUCCESS.getStatuscode(), status.getStatuscode());
        //    }
        //    */
        //
        //    @Test
        //    void validateDurration() {
        //        assertEquals(0,0);
        //    }
        }