package cinema.dao;

public interface GenericDao<T> {

    T add(T entity);

    T findById(Long id);
}
