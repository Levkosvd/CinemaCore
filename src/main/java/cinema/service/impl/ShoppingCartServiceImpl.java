package cinema.service.impl;

import cinema.dao.ShoppingCartDao;
import cinema.dao.TicketDao;
import cinema.lib.Inject;
import cinema.lib.Service;
import cinema.model.MovieSession;
import cinema.model.ShoppingCart;
import cinema.model.Ticket;
import cinema.model.User;
import cinema.service.ShoppingCartService;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    @Inject
    ShoppingCartDao shoppingCartDao;
    @Inject
    TicketDao ticketDao;

    @Override
    public void addSession(MovieSession movieSession, User user) {
        Ticket newTicket = new Ticket();
        newTicket.setCinemaHall(movieSession.getCinemaHall());
        newTicket.setMovie(movieSession.getMovie());
        newTicket.setShowTime(movieSession.getShowTime());
        newTicket.setUser(user);
        ticketDao.add(newTicket);
        ShoppingCart s = shoppingCartDao.getByUser(user);
        s.getTickets().add(newTicket);
        shoppingCartDao.update(s);
    }

    @Override
    public ShoppingCart getByUser(User user) {
        return shoppingCartDao.getByUser(user);
    }

    @Override
    public void registerNewShoppingCart(User user) {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUser(user);
        shoppingCartDao.add(shoppingCart);
    }
}
