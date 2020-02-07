package cinema.service;

import cinema.model.User;
import javax.security.sasl.AuthenticationException;

public interface AuthenticationService {

    User login(String email, String password)
              throws AuthenticationException, cinema.execeptions.AuthenticationException;

    User register(String email, String password);
}
