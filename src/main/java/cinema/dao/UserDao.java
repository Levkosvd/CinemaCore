package cinema.dao;

import cinema.model.User;

public interface UserDao extends GenericDao<User> {

    User findByEmail(String email);
}
