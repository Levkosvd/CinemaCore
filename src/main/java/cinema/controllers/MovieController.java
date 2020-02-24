package cinema.controllers;

import cinema.model.Movie;
import cinema.model.dto.request.MovieRequestDto;
import cinema.model.dto.response.MovieResponseDto;
import cinema.service.MovieService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/movies")
public class MovieController {
    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @RequestMapping(method = RequestMethod.POST)
    public String addMovie(@RequestBody MovieResponseDto responseDto) {
        Movie movie = new Movie();
        movie.setDescription(responseDto.getDescription());
        movie.setTitle(responseDto.getTitle());
        movieService.add(movie);
        return "Successful add of Movie!";
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<MovieRequestDto> getAllMovies() {
        return movieService.getAll()
                .stream()
                .map(this::getMovieDtoFromEntity)
                .collect(Collectors.toList());
    }

    private MovieRequestDto getMovieDtoFromEntity(Movie movie) {
        MovieRequestDto dtoMovie = new MovieRequestDto();
        dtoMovie.setDescription(movie.getDescription());
        dtoMovie.setTitle(movie.getTitle());
        return dtoMovie;
    }
}
