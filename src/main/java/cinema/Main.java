package cinema;

import cinema.lib.Injector;
import cinema.model.Movie;
import cinema.service.MovieService;

public class Main {
    private static Injector injector = Injector.getInstance("cinema");

    public static void main(String[] args) {
        Movie movie = new Movie();
        movie.setTitle("Fast and Furious");
        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        movieService.add(movie);
        movieService.getAll().forEach(System.out::println);
    }
}
