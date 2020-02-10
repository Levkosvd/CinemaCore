package cinema.service.impl;

import cinema.dao.OrderDao;
import cinema.lib.Inject;
import cinema.lib.Service;
import cinema.model.Order;
import cinema.model.ShoppingCart;
import cinema.model.User;
import cinema.service.OrderService;
import cinema.service.ShoppingCartService;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Inject
    private static OrderDao orderDao;
    @Inject
    private static ShoppingCartService shoppingCartService;

    @Override
    public Order completeOrder(User user) {
        ShoppingCart shoppingCart = shoppingCartService.getByUser(user);
        Order newOrder = new Order();
        newOrder.setLocalDateTime(LocalDateTime.now());
        newOrder.setTickets(shoppingCart.getTickets());
        newOrder.setUser(user);
        shoppingCartService.clear(shoppingCart);
        return orderDao.add(newOrder);
    }

    @Override
    public List<Order> getOrderHistory(User user) {
        return orderDao.getOrderHistory(user);
    }
}
