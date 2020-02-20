package cinema.service.impl;

import cinema.dao.MovieDao;
import cinema.model.Movie;
import cinema.service.MovieService;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class MovieServiceImpl implements MovieService {
    final MovieDao movieDao;

    public MovieServiceImpl(MovieDao movieDao) {
        this.movieDao = movieDao;
    }

    @Override
    public Movie add(Movie movie) {
        return movieDao.add(movie);
    }

    @Override
    public Movie findById(Long id) {
        return movieDao.findById(id);
    }

    @Override
    public List<Movie> getAll() {
        return movieDao.getAll();
    }
}
