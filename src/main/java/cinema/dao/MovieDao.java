package cinema.dao;

import cinema.model.Movie;
import java.util.List;

public interface MovieDao extends GenericDao<Movie> {

    List<Movie> getAll();
}
