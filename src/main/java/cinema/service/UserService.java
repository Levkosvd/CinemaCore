package cinema.service;

import cinema.lib.Service;
import cinema.model.User;

@Service
public interface UserService {

    User add(User user);

    User findByEmail(String email);
}
