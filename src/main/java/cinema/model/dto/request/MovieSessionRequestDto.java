package cinema.model.dto.request;

import java.time.LocalDateTime;

public class MovieSessionRequestDto {
    private Long movieId;
    private Long cinemaHallId;
    private LocalDateTime showTime;

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    public Long getCinemaHallId() {
        return cinemaHallId;
    }

    public void setCinemaHallId(Long cinemaHallId) {
        this.cinemaHallId = cinemaHallId;
    }

    public void setShowTime(LocalDateTime showTime) {
        this.showTime = showTime;
    }

    public LocalDateTime getShowTime() {
        return showTime;
    }
}
