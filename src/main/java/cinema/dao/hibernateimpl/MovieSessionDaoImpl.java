package cinema.dao.hibernateimpl;

import cinema.dao.MovieSessionDao;
import cinema.model.MovieSession;
import java.time.LocalDate;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

@Repository
public class MovieSessionDaoImpl implements MovieSessionDao {
    final SessionFactory sessionFactory;

    public MovieSessionDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public MovieSession add(MovieSession movieSession) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Long movieSessionId = (Long) session.save(movieSession);
            transaction.commit();
            movieSession.setId(movieSessionId);
            return movieSession;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't insert MovieSession entity", e);
        }
    }

    @Override
    public MovieSession findById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(MovieSession.class,id);
        } catch (Exception e) {
            throw new RuntimeException("Can't find MovieSession by id -" + id, e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<MovieSession> criteriaQuery  =
                    criteriaBuilder.createQuery(MovieSession.class);
            Root<MovieSession> movieSessionRoot =
                    criteriaQuery.from(MovieSession.class);
            Predicate predicateDate = criteriaBuilder
                    .between(movieSessionRoot.get("showTime"), date.atStartOfDay(),
                    date.plusDays(1).atStartOfDay());
            Predicate predicateId = criteriaBuilder.equal(movieSessionRoot.get("movie"), movieId);
            criteriaQuery
                    .select(movieSessionRoot)
                    .where(criteriaBuilder.and(predicateDate, predicateId));
            return session.createQuery(criteriaQuery).getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Can't get list of MovieSession entities", e);
        }
    }
}
