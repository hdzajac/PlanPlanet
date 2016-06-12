package pl.edu.agh.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;
import pl.edu.agh.model.auth.Credentials;
import pl.edu.agh.model.auth.LoginResponse;
import pl.edu.agh.model.user.User;
import pl.edu.agh.services.AuthenticationService;

import java.security.Principal;
import java.util.Optional;


@RestController
public class AuthenticationController {

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    @ResponseBody
    public Principal user(Principal user) {
        System.out.println(user);
        return user;
    }

    @Autowired
    @Qualifier("authenticationServiceImpl")
    private AuthenticationService authenticationService;

    @RequestMapping(value = "/user_login", method = RequestMethod.POST)
    public ResponseEntity<LoginResponse> login(@RequestBody Credentials credentials) {
        try {
            authenticationService.authenticateUser(credentials);
            System.out.println(credentials);
            final Optional<User> currentUser = authenticationService.getCurrentUser();
            if (currentUser.isPresent()) {
                return new ResponseEntity<>(LoginResponse.success(currentUser.get()),
                        HttpStatus.OK);
            }
            return new ResponseEntity<>(LoginResponse.failed(), HttpStatus.OK);
        } catch (AuthenticationException ex) {
            return new ResponseEntity<>(LoginResponse.failed(), HttpStatus.OK);
        }
    }

    @RequestMapping("logout")
    public ResponseEntity logout() {
        authenticationService.removeCurrentAuthenticationToken();
        return new ResponseEntity<>(true, HttpStatus.OK);
    }


}
