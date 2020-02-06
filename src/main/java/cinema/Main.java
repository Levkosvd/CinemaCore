package cinema;

import cinema.lib.Injector;
import cinema.model.CinemaHall;
import cinema.model.Movie;
import cinema.model.MovieSession;
import cinema.service.CinemaHallService;
import cinema.service.MovieService;
import cinema.service.MovieSessionService;
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

        //right session
        MovieSession movieSession1 = new MovieSession();
        movieSession1.setMovie(movie);
        movieSession1.setCinemaHall(cinemaHall);
        movieSession1.setShowTime(LocalDateTime.of(LocalDate.now(),
                LocalTime.of(12,30)));
        movieSessionService.add(movieSession1);

        //another right session
        MovieSession movieSession2 = new MovieSession();
        movieSession2.setMovie(movie);
        movieSession2.setCinemaHall(cinemaHall);
        movieSession2.setShowTime(LocalDateTime.of(LocalDate.now(),
                LocalTime.of(15,30)));
        movieSessionService.add(movieSession2);

        //session with another date
        MovieSession movieSession3 = new MovieSession();
        movieSession3.setMovie(movie);
        movieSession3.setCinemaHall(cinemaHall);
        movieSession3.setShowTime(LocalDateTime.of(LocalDate.now().minusDays(1),
                LocalTime.of(19,30)));
        movieSessionService.add(movieSession3);

        //session with another movie
        MovieSession movieSession4 = new MovieSession();
        movieSession4.setMovie(movie2);
        movieSession4.setCinemaHall(cinemaHall);
        movieSession4.setShowTime(LocalDateTime.of(LocalDate.now(),
                LocalTime.of(17,30)));
        movieSessionService.add(movieSession4);

        movieSessionService.findAvailableSessions(movie.getId(),
                LocalDate.now()).forEach(System.out::println);
        /* Output :
        MovieSession{id=1, movie=Movie{id=1, title='Fast and Furious', description='null'},
        cinemaHall=CinemaHall{id=1, description='CinemaCity', capacity=250},
        showTime=2020-02-05T12:30}

        MovieSession{id=2, movie=Movie{id=1, title='Fast and Furious', description='null'},
        cinemaHall=CinemaHall{id=1, description='CinemaCity', capacity=250},
        showTime=2020-02-05T15:30}
         */
    }
}
