package cinema.dao.hibernateimpl;

import cinema.dao.UserDao;
import cinema.model.User;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl implements UserDao {
    final SessionFactory sessionFactory;

    public UserDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public User add(User user) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Long movieId = (Long) session.save(user);
            transaction.commit();
            user.setId(movieId);
            return user;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't insert User entity", e);
        }
    }

    @Override
    public User findById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(User.class,id);
        } catch (Exception e) {
            throw new RuntimeException("Can't find User by id -" + id, e);
        }
    }

    @Override
    public User findByEmail(String email) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<User> userCriteriaQuery =
                    criteriaBuilder.createQuery(User.class);
            Root<User> userRoot =
                    userCriteriaQuery.from(User.class);
            userCriteriaQuery
                    .select(userRoot)
                    .where(criteriaBuilder.and(criteriaBuilder
                            .equal(userRoot.get("email"), email)));
            return session.createQuery(userCriteriaQuery).uniqueResult();
        } catch (Exception e) {
            throw new RuntimeException("Can't find User by email -" + email, e);
        }
    }
}
