package cinema.controllers;

import cinema.model.MovieSession;
import cinema.model.dto.request.MovieSessionRequestDto;
import cinema.service.CinemaHallService;
import cinema.service.MovieService;
import cinema.service.MovieSessionService;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/moviesessions")
public class MovieSessionController {
    final MovieSessionService movieSessionService;
    final CinemaHallService cinemaHallService;
    final MovieService movieService;

    public MovieSessionController(MovieSessionService movieSessionService,
                                  CinemaHallService cinemaHallService,
                                  MovieService movieService) {
        this.movieSessionService = movieSessionService;
        this.cinemaHallService = cinemaHallService;
        this.movieService = movieService;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public void addMovieSession(@RequestBody MovieSessionRequestDto requestDto) {
        movieSessionService.add(getMovieSessionFromDto(requestDto));
    }

    @RequestMapping(value = "/available",method = RequestMethod.GET)
    public List<MovieSessionRequestDto> availableSessions(@RequestParam(name = "movieId")Long id,
                                                          @RequestParam(name = "date")String date) {
        MovieSessionRequestDto movieSessionRequestDto = new MovieSessionRequestDto();
        return movieSessionService.findAvailableSessions(id,
                LocalDate.parse(date, DateTimeFormatter.ofPattern("dd.MM.yyyy")))
                .stream()
                .map(MovieSessionController::getMovieSessionDtoFromEntity)
                .collect(Collectors.toList());
    }

    private MovieSession getMovieSessionFromDto(MovieSessionRequestDto requestDto) {
        MovieSession movieSession = new MovieSession();
        movieSession.setCinemaHall(cinemaHallService.findById(requestDto.getCinemaHallId()));
        movieSession.setMovie(movieService.findById(requestDto.getMovieId()));
        movieSession.setShowTime(requestDto.getShowTime());
        return movieSession;
    }

    private static MovieSessionRequestDto getMovieSessionDtoFromEntity(MovieSession movieSession) {
        MovieSessionRequestDto dtoMovieSession = new MovieSessionRequestDto();
        dtoMovieSession.setCinemaHallId(movieSession.getCinemaHall().getId());
        dtoMovieSession.setShowTime(movieSession.getShowTime());
        dtoMovieSession.setMovieId(movieSession.getMovie().getId());
        return dtoMovieSession;
    }
}
