package cinema.dao.hibernateimpl;

import cinema.dao.ShoppingCartDao;
import cinema.model.ShoppingCart;
import cinema.model.User;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

@Repository
public class ShoppingCartDaoImpl implements ShoppingCartDao {
    final SessionFactory sessionFactory;

    public ShoppingCartDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public ShoppingCart add(ShoppingCart shoppingCart) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Long shoppingCartId = (Long) session.save(shoppingCart);
            transaction.commit();
            shoppingCart.setId(shoppingCartId);
            return shoppingCart;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't insert User entity", e);
        }
    }

    @Override
    public ShoppingCart findById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(ShoppingCart.class,id);
        } catch (Exception e) {
            throw new RuntimeException("Can't find ShoppingCart by id -" + id, e);
        }
    }

    @Override
    public ShoppingCart getByUser(User user) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<ShoppingCart> criteriaQuery =
                    criteriaBuilder.createQuery(ShoppingCart.class);
            Root<ShoppingCart> root = criteriaQuery.from(ShoppingCart.class);
            criteriaQuery
                    .select(root)
                    .where(criteriaBuilder.and(criteriaBuilder
                            .equal(root.get("user"), user)));
            ShoppingCart shoppingCartFromBd = session.createQuery(criteriaQuery).uniqueResult();
            Hibernate.initialize(shoppingCartFromBd.getTickets());
            return shoppingCartFromBd;
        }
    }

    @Override
    public void update(ShoppingCart shoppingCart) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.update(shoppingCart);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't insert ShoppingCart entity", e);
        }
    }

}
