package cinema.dao;

import cinema.model.Order;
import cinema.model.User;
import java.util.List;

public interface OrderDao extends GenericDao<Order> {

    List<Order> getOrderHistory(User user);
}
