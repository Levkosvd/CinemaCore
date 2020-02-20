package cinema.service;

import cinema.model.Movie;
import java.util.List;

public interface MovieService {

    Movie add(Movie movie);

    List<Movie> getAll();

    Movie findById(Long id);
}
