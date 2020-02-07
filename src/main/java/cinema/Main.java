package cinema;

import cinema.lib.Injector;
import cinema.model.CinemaHall;
import cinema.model.Movie;
import cinema.model.MovieSession;
import cinema.model.User;
import cinema.service.AuthenticationService;
import cinema.service.CinemaHallService;
import cinema.service.MovieService;
import cinema.service.MovieSessionService;
import cinema.service.ShoppingCartService;
import cinema.service.UserService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Main {
    private static Injector injector = Injector.getInstance("cinema");

    public static void main(String[] args) {
        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);
        MovieService movieService =
                (MovieService) injector.getInstance(MovieService.class);
        CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);
        UserService userService =
                (UserService) injector.getInstance(UserService.class);
        AuthenticationService authService =
                (AuthenticationService) injector.getInstance(AuthenticationService.class);
        ShoppingCartService shoppingCartService =
                (ShoppingCartService) injector.getInstance(ShoppingCartService.class);

        Movie movie = new Movie();
        movie.setTitle("Fast and Furious");
        movieService.add(movie);
        movieService.getAll().forEach(System.out::println);

        Movie movie2 = new Movie();
        movie2.setTitle("Hateful Eight");
        movieService.add(movie2);
        movieService.getAll().forEach(System.out::println);

        CinemaHall cinemaHall = new CinemaHall();
        cinemaHall.setCapacity(250);
        cinemaHall.setDescription("CinemaCity");
        cinemaHall = cinemaHallService.add(cinemaHall);

        MovieSession movieSession1 = new MovieSession();
        movieSession1.setMovie(movie);
        movieSession1.setCinemaHall(cinemaHall);
        movieSession1.setShowTime(LocalDateTime.of(LocalDate.now(),
                LocalTime.of(12,30)));
        movieSessionService.add(movieSession1);

        MovieSession movieSession2 = new MovieSession();
        movieSession2.setMovie(movie2);
        movieSession2.setCinemaHall(cinemaHall);
        movieSession2.setShowTime(LocalDateTime.of(LocalDate.now(),
                LocalTime.of(15,30)));
        movieSessionService.add(movieSession2);

        String email = "login@gmail.com";
        String password = "1111";
        authService.register(email, password);
        User user = userService.findByEmail("login@gmail.com");
        shoppingCartService.addSession(movieSession1, user);
        shoppingCartService.addSession(movieSession2, user);
    }
}
