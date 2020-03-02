package cinema.controllers;

import cinema.model.CinemaHall;
import cinema.model.dto.request.CinemaHallRequestDto;
import cinema.model.dto.response.CinemaHallResponseDto;
import cinema.service.CinemaHallService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cinemahalls")
public class CinemaHallController {
    private final CinemaHallService cinemaHallService;

    public CinemaHallController(CinemaHallService cinemaHallService) {
        this.cinemaHallService = cinemaHallService;
    }

    @RequestMapping(method = RequestMethod.POST)
    public String addCinemaHall(@RequestBody CinemaHallResponseDto responseDto) {
        CinemaHall cinemaHall = new CinemaHall();
        cinemaHall.setDescription(responseDto.getDescription());
        cinemaHall.setCapacity(responseDto.getCapacity());
        cinemaHallService.add(cinemaHall);
        return "Successful add of CinemaHall!";
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<CinemaHallRequestDto> getAllCinemaHalls() {
        return cinemaHallService.getAll()
                .stream()
                .map(this::getCinemaHallDtoFromEntity)
                .collect(Collectors.toList());

    }

    private  CinemaHallRequestDto getCinemaHallDtoFromEntity(CinemaHall cinemaHall) {
        CinemaHallRequestDto dtoMovie = new CinemaHallRequestDto();
        dtoMovie.setDescription(cinemaHall.getDescription());
        dtoMovie.setCapacity(cinemaHall.getCapacity());
        return dtoMovie;
    }
}
