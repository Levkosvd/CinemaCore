package cinema.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

public interface GenericDao<T> {

    T add(T entity);
}
