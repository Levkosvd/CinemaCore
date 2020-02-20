package cinema.controllers;

import cinema.service.AuthenticationService;
import javax.security.sasl.AuthenticationException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {
    final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public void login(@RequestParam(name = "email") String email,
                      @RequestParam(name = "password") String password)
            throws AuthenticationException, cinema.execeptions.AuthenticationException {
        authenticationService.login(email, password);
    }

    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public void register(@RequestParam(name = "email") String email,
                      @RequestParam(name = "password") String password) {
        authenticationService.register(email, password);
    }

}
