package de.cookiebook.restservice.user;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;
@RestController
@Slf4j
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {
    @Autowired
    UserRepository userRepository;
    @PostMapping("/users/register")
    public long registerUser(@Valid @RequestBody User newUser) { // UserId als String mitgegeben ans Frontend
        List<User> users = userRepository.findAll();
        System.out.println("New user: " + newUser.toString());
        for (User user : users) {
            System.out.println("Registered user: " + newUser.toString());
            if (user.equals(newUser)) {
                System.out.println("User Already exists!");
                log.warn(Status.USER_ALREADY_EXISTS.toString());
                return Status.USER_ALREADY_EXISTS.getStatuscode();
            }
        }
        userRepository.save(newUser);
        log.info(Status.SUCCESS.toString());
        return newUser.getId();
    }

    @PostMapping("/users/login")
    public long loginUser(@Valid @RequestBody User user) { // UserId als String mitgegeben ans Frontend
        List<User> users = userRepository.findAll();
        for (User other : users) {
            if (other.equals(user)) {
                user.setLoggedIn(true);
                userRepository.save(user);
                return user.getId();
            }
        }
        return Status.FAILURE.getStatuscode();
    }

    @PostMapping("/users/logout") // cookies/token setzen, server validierung austauschen
    public Status logUserOut(@Valid @RequestBody User user) {
        List<User> users = userRepository.findAll();
        for (User other : users) {
            if (other.equals(user)) {
                user.setLoggedIn(false);
                userRepository.save(user);
                return Status.SUCCESS;
            }
        }
        return Status.FAILURE;
    }

    @GetMapping("/userlist")
    public List<User> getUsers(){
        return userRepository.findAll();
    }
    
    // edit profile
    
    @PostMapping("/users/edit")
    public User editProfile(@Valid @RequestBody User user) {
        userRepository.save(user);
        System.out.println(user);
        return user;
    }
    
    @PostMapping("/users/changePassword")
    public User changePassword(@Valid @RequestBody User user, @RequestBody String newPassword, HttpServletResponse response) {
    	List<User> users = userRepository.findAll();
    	for (User other : users) {
            if (other.equals(user)) {
                user.setPassword(newPassword);
                userRepository.save(user);
                return user;
            }
        } 
    	response.setStatus(HttpServletResponse.SC_FORBIDDEN);
    	return null;  	
    }   
}