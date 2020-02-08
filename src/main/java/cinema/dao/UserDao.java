package cinema.dao;

import cinema.model.User;

public interface UserDao {

    User add(User user);

    User findByEmail(String email);
}
