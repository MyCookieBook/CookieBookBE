package de.cookiebook.restservice.user;

import de.cookiebook.restservice.JWT.JWTAuthenticationResponse;
import de.cookiebook.restservice.JWT.JWTTokenUtil;
import de.cookiebook.restservice.service.AuthenticationUserDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {
    @Autowired
    UserRepository userRepository;
    AuthenticationManager authenticationManager;
    AuthenticationUserDetailService authenticationUserDetailService;
    JWTTokenUtil jwtTokenUtil;

    /**
     * Zhibek fragen was genau in der Methode alles in der Datenbank gemacht wird
     * Zhibek soll es ausführen am besten mit Postman , da bei mir immer 403 Forbidden error geworfen wurde
     */
    @PostMapping("/users/register")
    public long registerUser(@Valid @RequestBody User newUser) { // UserId als String mitgegeben ans Frontend
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
        userRepository.save(newUser);
        log.info(Status.SUCCESS.toString());
        log.info(String.valueOf(newUser.getId()));
        return newUser.getId();
    }

    //    User body muss email und password beinhalten
    @PostMapping("/users/login")
    public long loginUser(@Valid @RequestBody User user) { // UserId als String mitgegeben ans Frontend
        try {
            List<User> users = userRepository.findAll();
            for (User other : users) {
                if ((other.getEmail()).equals(user.getEmail()) && (other.getPassword()).equals(user.getPassword())) {
                    log.info(String.valueOf(user.getId()));
                    log.info(String.valueOf(other.getId()));
                    other.setLoggedIn(true);
                    userRepository.save(other);
//                    String username = authenticationLogin(user);
//
//                    if (user.isLoggedIn()) {
//                        UserDetails userDetails = authenticationUserDetailService.loadUserByUsername(username);
//                        final String token = jwtTokenUtil.generateToken(userDetails);
//                        return ResponseEntity.ok(new JWTAuthenticationResponse(token));
//                    }

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
//                Token soll deaktiviert werden deactivateToken() bzw das expire date setzen
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

    private String authenticationLogin(@RequestBody User user) {
        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword())
        );
            return user.getEmail();
        } catch (Exception e) {
            log.error(e.getMessage());
            return "failure";
        }
    }
}