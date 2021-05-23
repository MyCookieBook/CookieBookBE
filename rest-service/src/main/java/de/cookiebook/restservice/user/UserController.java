package de.cookiebook.restservice.user;

import de.cookiebook.restservice.JWT.JWTAuthenticationResponse;
import de.cookiebook.restservice.JWT.JWTTokenUtil;
import de.cookiebook.restservice.config.AuthenticationConfigConstants;
import de.cookiebook.restservice.service.AuthenticationUserDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {
    UserRepository userRepository;
    AuthenticationManager authenticationManager;
    AuthenticationUserDetailService authenticationUserDetailService;
    JWTTokenUtil jwtTokenUtil;

    @PostMapping("/users/register")
    public long registerUser(@Valid @RequestBody User newUser) {
        List<User> users = userRepository.findAll();
        log.info("test1");
        System.out.println("New user: " + newUser.toString());
        for (User user : users) {
            log.info("test2");
            System.out.println("Registered user: " + newUser.toString());
            if ((user.getEmail()).equals(newUser.getEmail())) {
                log.info("test3");
                System.out.println("User Already exists!");
                log.warn(Status.USER_ALREADY_EXISTS.toString());
                return Status.USER_ALREADY_EXISTS.getStatuscode();
            }
        }
        newUser.setDurration(new Date(System.currentTimeMillis()+ AuthenticationConfigConstants.EXPIRATION_TIME));
        userRepository.save(newUser);
        log.info(Status.SUCCESS.toString());
        log.info(String.valueOf(newUser.getId()));
        return newUser.getId();
    }

    //    User body muss email und password beinhalten
    @PostMapping("/users/login")
    public long loginUser(@Valid @RequestBody User user) {
        try {
            List<User> users = userRepository.findAll();
            for (User other : users) {
                if ((other.getEmail()).equals(user.getEmail()) && (other.getPassword()).equals(user.getPassword())) {
                    log.info(String.valueOf(user.getId()));
                    log.info(String.valueOf(other.getId()));
                    other.setLoggedIn(true);
                    other.setDurration(new Date(System.currentTimeMillis()+ AuthenticationConfigConstants.EXPIRATION_TIME));
                    userRepository.save(other);
                    return other.getId();
                }
            }
            return Status.FAILURE.getStatuscode();
        } catch (Exception e) {
            log.error(e.getMessage());
            return Status.FAILURE.getStatuscode();
        }
    }

    @PostMapping("/users/logout")
    public Status logUserOut(@Valid @RequestBody User user) {
        List<User> users = userRepository.findAll();
        for (User other : users) {
            if ((other.getEmail()).equals(user.getEmail()) && (other.getPassword()).equals(user.getPassword())) {
                other.setLoggedIn(false);
                userRepository.save(other);
                return Status.SUCCESS;
            }
        }
        return Status.FAILURE;
    }

    @GetMapping("/userlist")
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public boolean validateDurration(User user) {
        try {
            Date expiration = user.getDurration();
            if(!expiration.before(new Date())){
                logUserOut(user);
                return false;
            }
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }
}