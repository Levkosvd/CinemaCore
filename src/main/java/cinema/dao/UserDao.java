package cinema.dao;

import cinema.model.User;

public interface UserDao extends GenericDao<User> {

    User add(User user);

    User findByEmail(String email);
}
