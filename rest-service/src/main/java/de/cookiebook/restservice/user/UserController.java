package de.cookiebook.restservice.user;

import de.cookiebook.restservice.config.AuthenticationConfigConstants;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        try {
            List<User> users = userRepository.findAll();
            for (User user : users) {
                if ((user.getEmail()).equals(newUser.getEmail())) {
                    System.out.println("User Already exists!");
                    log.warn(Status.USER_ALREADY_EXISTS.toString());
                    return Status.USER_ALREADY_EXISTS.getStatuscode();
                }
            }
            newUser.setDuration(new Date(System.currentTimeMillis() + AuthenticationConfigConstants.EXPIRATION_TIME));
            userRepository.save(newUser);
            log.info(Status.SUCCESS.toString());
            log.info(String.valueOf(newUser.getId()));
            return newUser.getId();
        } catch (Exception e) {
            log.error("failed register user");
            log.error(e.getMessage());
            return Status.FAILURE.getStatuscode();
        }
    }

    @CrossOrigin(origins = "http://localhost:4200/login")
    @PostMapping("/users/login")
    public long loginUser(@RequestParam(value = "email") String email, @RequestParam(value = "password") String password) {
        log.info(email);
        log.info(password);
        try {
            List<User> users = userRepository.findAll();
            for (User other : users) {
                if ((other.getEmail()).equals(email) && (other.getPassword()).equals(password)) {
                    log.info(String.valueOf(other.getId()));
                    other.setLoggedIn(true);
                    other.setDuration(new Date(System.currentTimeMillis() + AuthenticationConfigConstants.EXPIRATION_TIME));
                    userRepository.save(other);
                    return other.getId();
                }
            }
            log.error(String.valueOf(Status.FAILURE.getStatuscode()));
            return Status.FAILURE.getStatuscode();
        } catch (Exception e) {
            log.error("failed log in user");
            log.error(e.getMessage());
            return Status.FAILURE.getStatuscode();
        }
    }

    @PostMapping("/users/logout")
    public long logUserOut(@RequestParam(value = "userId") long userId) {
        List<User> users = userRepository.findAll();
        for (User other : users) {
            if (other.getId() == userId) {
                other.setLoggedIn(false);
                other.setDuration(null);
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
    public String[] getUser(@RequestParam(value = "UserId") long userId) {
        try {
            String[] userArray = new String[2];
            List<User> userlist = userRepository.findAll();
            for (User user : userlist) {
                if (user.getId() == userId) {
                    if (validateDurration(user)) {
                        userArray[0] = user.getEmail();
                        userArray[1] = user.getUsername();
                        return userArray;
                    } else {
                        return null;
                    }
                }
            }
            return null;
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    // testen bzw manuell ändern der email adresse
//     nicht um von außen drauf zu zugreifen
//    @PostMapping("/changeEmail")
//    public Status changeEmail(@RequestParam(value = "userId") long userId, @RequestParam(value = "email") String email) {
//        try {
//            List<User> users = userRepository.findAll();
//            for (User other : users) {
//                if (other.getId() == userId) {
//                    other.setEmail(email);
//                    userRepository.save(other);
//                    return Status.SUCCESS;
//                }
//            }
//            return Status.FAILURE;
//        } catch (Exception e) {
//            log.error(e.getMessage());
//            return Status.FAILURE;
//        }
//    }

    public boolean validateDurration(User user) {
        try {
            Date expiration = user.getDuration();
            Date time = new Timestamp(System.currentTimeMillis());
            if (!time.before(expiration)) {
                logUserOut(user.getId());
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }

    // edit profile
    @PostMapping("/users/edit")
    public long editProfile(@Valid @RequestBody User user) {
        try {
            User userBefore = userRepository.findById(user.getId());
            if (validateDurration(userBefore)) {
                if (user.getPassword() == null) {
                    user.setPassword(userBefore.getPassword());
                }
                user.setLoggedIn(true);
                user.setDuration(userBefore.getDuration());
                userRepository.save(user);
                System.out.println(user);
                return Status.SUCCESS.getStatuscode();
            } else {
                return Status.FAILURE.getStatuscode();
            }

        } catch (Exception e) {
            log.error(e.getMessage());
            return Status.FAILURE.getStatuscode();
        }
    }

    @PostMapping("/users/changePassword")
    public long changePassword(@Valid @RequestBody User user, @RequestParam(value = "newPassword") String newPassword, HttpServletResponse response) {
        try {
            List<User> users = userRepository.findAll();
            for (User other : users) {
                if (other.getEmail().equals(user.getEmail()) && other.getId() == user.getId()) {
                    if (validateDurration(other)) {
                        user.setPassword(newPassword);
                        user.setLoggedIn(true);
                        user.setDuration(other.getDuration());
                        userRepository.save(user);
                        return Status.SUCCESS.getStatuscode();
                    } else {
                        return Status.FAILURE.getStatuscode();
                    }
                }
            }
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return Status.FAILURE.getStatuscode();

        } catch (Exception e) {
            log.error(e.getMessage());
            return Status.FAILURE.getStatuscode();
        }
    }
}