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
import cinema.service.UserService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import javax.security.sasl.AuthenticationException;

public class Main {
    private static Injector injector = Injector.getInstance("cinema");

    public static void main(String[] args) throws AuthenticationException, cinema.execeptions.AuthenticationException {
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
        movieSession2.setMovie(movie);
        movieSession2.setCinemaHall(cinemaHall);
        movieSession2.setShowTime(LocalDateTime.of(LocalDate.now(),
                LocalTime.of(15,30)));
        movieSessionService.add(movieSession2);

        MovieSession movieSession3 = new MovieSession();
        movieSession3.setMovie(movie);
        movieSession3.setCinemaHall(cinemaHall);
        movieSession3.setShowTime(LocalDateTime.of(LocalDate.now().minusDays(1),
                LocalTime.of(19,30)));
        movieSessionService.add(movieSession3);

        MovieSession movieSession4 = new MovieSession();
        movieSession4.setMovie(movie2);
        movieSession4.setCinemaHall(cinemaHall);
        movieSession4.setShowTime(LocalDateTime.of(LocalDate.now(),
                LocalTime.of(17,30)));
        movieSessionService.add(movieSession4);

        String email = "login@gmail.com";
        String password = "1111";
        authService.register(email, password);

        System.out.println(userService.findByEmail("login@gmail.com"));

        System.out.println(authService.login(email, password));


        System.out.println(authService.login(email, "2222"));


        movieSessionService.findAvailableSessions(movie.getId(),
                LocalDate.now()).forEach(System.out::println);



    }
}
