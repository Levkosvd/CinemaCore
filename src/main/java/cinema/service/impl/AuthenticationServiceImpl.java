package cinema.service.impl;

import cinema.dao.UserDao;
import cinema.execeptions.AuthenticationException;
import cinema.model.User;
import cinema.service.AuthenticationService;
import cinema.service.ShoppingCartService;
import cinema.util.HashUtil;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    final UserDao userDao;
    final ShoppingCartService shoppingCartService;

    public AuthenticationServiceImpl(UserDao userDao, ShoppingCartService shoppingCartService) {
        this.userDao = userDao;
        this.shoppingCartService = shoppingCartService;
    }

    @Override
    public User login(String email, String password) throws AuthenticationException {
        User userFromDb = userDao.findByEmail(email);
        if (userFromDb == null || !userFromDb.getPassword()
                .equals(HashUtil.hashPassword(password,userFromDb.getSalt()))) {
            throw new AuthenticationException("Incorrect login or password!");
        }
        return userFromDb;
    }

    @Override
    public User register(String email, String password) {
        User newUser = new User();
        newUser.setEmail(email);
        newUser.setSalt(HashUtil.getRandomSalt());
        newUser.setPassword(HashUtil.hashPassword(password, newUser.getSalt()));
        User userfromDb = userDao.add(newUser);
        shoppingCartService.registerNewShoppingCart(newUser);
        return userfromDb;
    }
}
