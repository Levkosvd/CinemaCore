package cinema.dao;

import cinema.lib.Dao;
import cinema.model.User;


public interface UserDao {

    User add(User user);

    User findByEmail(String email);
}
