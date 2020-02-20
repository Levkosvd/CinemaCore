package cinema.controllers;

import cinema.model.MovieSession;
import cinema.model.ShoppingCart;
import cinema.model.dto.request.MovieSessionRequestDto;
import cinema.model.dto.request.ShoppingCartRequestDto;
import cinema.service.CinemaHallService;
import cinema.service.MovieService;
import cinema.service.ShoppingCartService;
import cinema.service.UserService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/shoppingcarts")
public class ShoppingCartController {
    final ShoppingCartService shoppingCartService;
    final UserService userService;
    final CinemaHallService cinemaHallService;
    final MovieService movieService;

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
    public void addMovieSession(@RequestBody MovieSessionRequestDto requestDto,
                                @RequestParam(name = "userId")Long userId) {
        MovieSession movieSession = new MovieSession();
        movieSession.setCinemaHall(cinemaHallService.findById(requestDto.getCinemaHallId()));
        movieSession.setMovie(movieService.findById(requestDto.getMovieId()));
        movieSession.setShowTime(requestDto.getShowTime());
        shoppingCartService.addSession(movieSession, userService.findById(userId));
    }

    @RequestMapping(value = "/byuser", method = RequestMethod.GET)
    public ShoppingCartRequestDto getByUser(@RequestParam(name = "userId")Long userId) {
        return getShoppingCartDtoFromEntity(shoppingCartService
                .getByUser(userService.findById(userId)));
    }

    private static ShoppingCartRequestDto getShoppingCartDtoFromEntity(ShoppingCart shoppingCart) {
        ShoppingCartRequestDto shopCartDto = new ShoppingCartRequestDto();
        shopCartDto.setTickets(shoppingCart.getTickets());
        shopCartDto.setUserId(shoppingCart.getUser().getId());
        shopCartDto.setId(shoppingCart.getId());
        return shopCartDto;
    }
}
