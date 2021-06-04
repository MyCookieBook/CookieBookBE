package de.cookiebook.restservice.user;

import de.cookiebook.restservice.config.AuthenticationConfigConstants;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@RestController
@Slf4j
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {
    @Autowired
    UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/users/register")
    public long registerUser(@Valid @RequestBody User newUser) {
        List<User> users = userRepository.findAll();
        log.info("test1");
        for (User user : users) {
            log.info("test2");
            if ((user.getEmail()).equals(newUser.getEmail())) {
                log.info("test3");
                System.out.println("User Already exists!");
                log.warn(Status.USER_ALREADY_EXISTS.toString());
                return Status.USER_ALREADY_EXISTS.getStatuscode();
            }
        }

        newUser.setDurration(new Date(System.currentTimeMillis() + AuthenticationConfigConstants.EXPIRATION_TIME));
        userRepository.save(newUser);
        log.info(Status.SUCCESS.toString());
        log.info(String.valueOf(newUser.getId()));
        return newUser.getId();
    }

    @CrossOrigin(origins = "http://localhost:4200/login")
    @PostMapping("/users/login")
    public long loginUser(@RequestParam(value = "email") String email, @RequestParam(value = "password") String password) {
        try {
            List<User> users = userRepository.findAll();
            for (User other : users) {
                if ((other.getEmail()).equals(email) && (other.getPassword()).equals(password)) {
                    log.info(String.valueOf(other.getId()));
                    other.setLoggedIn(true);
                    other.setDurration(new Date(System.currentTimeMillis() + AuthenticationConfigConstants.EXPIRATION_TIME));
                    userRepository.save(other);
                    return other.getId();
                }
            }
            log.error(String.valueOf(Status.FAILURE.getStatuscode()));
            return Status.FAILURE.getStatuscode();
        } catch (Exception e) {
            log.error(e.getMessage());
            return Status.FAILURE.getStatuscode();
        }
    }

    @PostMapping("/users/logout")
    public long logUserOut(@RequestParam(value = "userId") long userId) {
        List<User> users = userRepository.findAll();
        for (User other : users) {
            if (other.getId()==userId){
                other.setLoggedIn(false);
                other.setDurration(null);
                userRepository.save(other);
                return Status.SUCCESS.getStatuscode();
            }
        }
        return Status.FAILURE.getStatuscode();
    }

    @GetMapping("/userlist")
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/user")
    public User getUser(@RequestParam(value = "email") String email){
        List<User> userlist = userRepository.findAll();
        for (User user : userlist) {
            if ((user.getEmail()).equals(email)) {
                return user;
            }
        }
        return new User();
    }

    @PostMapping("/changeEmail")
    public Status changeEmail(@RequestParam(value = "userId") long userId, @RequestParam(value = "email") String email) {
        List<User> users = userRepository.findAll();
        for (User other : users) {
            if (other.getId()==userId){
                other.setEmail(email);
                userRepository.save(other);
                return Status.SUCCESS;
            }
        }
        return Status.FAILURE;
    }

    public void validateDurration(User user) {
        try {
            Date expiration = user.getDurration();
            if (!expiration.before(new Date())) {
                logUserOut(user.getId());
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
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