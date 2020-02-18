package cinema.dao;

import cinema.model.ShoppingCart;
import cinema.model.User;

public interface ShoppingCartDao extends GenericDao<ShoppingCart> {
        
    ShoppingCart getByUser(User user);
        
    void update(ShoppingCart shoppingCart);
}
