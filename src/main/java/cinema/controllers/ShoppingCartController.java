package cinema.controllers;

import cinema.model.MovieSession;
import cinema.model.ShoppingCart;
import cinema.model.Ticket;
import cinema.model.dto.request.MovieSessionRequestDto;
import cinema.model.dto.request.ShoppingCartRequestDto;
import cinema.model.dto.request.TicketRequestDto;
import cinema.service.CinemaHallService;
import cinema.service.MovieService;
import cinema.service.ShoppingCartService;
import cinema.service.UserService;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/shoppingcarts")
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;
    private final UserService userService;
    private final CinemaHallService cinemaHallService;
    private final MovieService movieService;

    public ShoppingCartController(ShoppingCartService shoppingCartService,
                                  UserService userService,
                                  CinemaHallService cinemaHallService,
                                  MovieService movieService) {
        this.shoppingCartService = shoppingCartService;
        this.userService = userService;
        this.cinemaHallService = cinemaHallService;
        this.movieService = movieService;
    }

    @RequestMapping(value = "/addmoviesession", method = RequestMethod.POST)
    public String addMovieSession(@RequestBody MovieSessionRequestDto requestDto,
                                @RequestParam(name = "userId")Long userId) {
        MovieSession movieSession = new MovieSession();
        movieSession.setCinemaHall(cinemaHallService.findById(requestDto.getCinemaHallId()));
        movieSession.setMovie(movieService.findById(requestDto.getMovieId()));
        movieSession.setShowTime(requestDto.getShowTime());
        shoppingCartService.addSession(movieSession, userService.findById(userId));
        return "Successful adding MovieSession to ShoppingCart";
    }

    @RequestMapping(value = "/byuser", method = RequestMethod.GET)
    public ShoppingCartRequestDto getByUser(@RequestParam(name = "userId")Long userId) {
        return getShoppingCartDtoFromEntity(shoppingCartService
                .getByUser(userService.findById(userId)));
    }

    private ShoppingCartRequestDto getShoppingCartDtoFromEntity(ShoppingCart shoppingCart) {
        ShoppingCartRequestDto shopCartDto = new ShoppingCartRequestDto();
        shopCartDto.setTickets(shoppingCart.getTickets().stream()
                .map(this::getThicketDtoFromTicket)
                .collect(Collectors.toList()));
        shopCartDto.setUserId(shoppingCart.getUser().getId());
        shopCartDto.setId(shoppingCart.getId());
        return shopCartDto;
    }
    private TicketRequestDto getThicketDtoFromTicket(Ticket entity) {
        TicketRequestDto ticket = new TicketRequestDto();
        ticket.setShowTime(entity.getShowTime());
        ticket.setCinemaHallId(entity.getCinemaHall().getId());
        ticket.setMovieId(entity.getMovie().getId());
        ticket.setUserId(entity.getUser().getId());
        ticket.setId(entity.getId());
        return ticket;
    }
}
