package cinema.service.impl;

import cinema.dao.UserDao;
import cinema.execeptions.AuthenticationException;
import cinema.lib.Inject;
import cinema.lib.Service;
import cinema.model.User;
import cinema.service.AuthenticationService;
import cinema.util.HashUtil;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Inject
    UserDao userDao;

    @Override
    public User login(String email, String password) throws AuthenticationException {
        User currentUser = userDao.findByEmail(email);
        if(currentUser == null || !currentUser.getPassword()
                .equals(HashUtil.hashPassword(password,currentUser.getSalt()))) {
            throw new AuthenticationException("Incorrect login or password!");
        }
        return currentUser;
    }

    @Override
    public User register(String email, String password) {
        User newUser = new User();
        newUser.setEmail(email);
        newUser.setSalt(HashUtil.getRandomSalt());
        newUser.setPassword(HashUtil.hashPassword(password, newUser.getSalt()));
        return userDao.add(newUser);
    }
}
