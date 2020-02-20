package cinema.dao.hibernateimpl;

import cinema.dao.OrderDao;
import cinema.model.Order;
import cinema.model.User;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

@Repository
public class OrderDaoImpl implements OrderDao {
    final SessionFactory sessionFactory;

    public OrderDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Order add(Order entity) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Long orderId = (Long) session.save(entity);
            transaction.commit();
            entity.setId(orderId);
            return entity;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't insert Order entity", e);
        }
    }

    @Override
    public Order findById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Order.class,id);
        } catch (Exception e) {
            throw new RuntimeException("Can't find Order by id -" + id, e);
        }
    }

    @Override
    public List<Order> getOrderHistory(User user) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Order> criteriaQuery =
                    criteriaBuilder.createQuery(Order.class);
            Root<Order> root = criteriaQuery.from(Order.class);
            criteriaQuery
                    .select(root)
                    .where(criteriaBuilder.and(criteriaBuilder
                            .equal(root.get("user"), user)));
            List<Order> userOrders = session.createQuery(criteriaQuery).getResultList();
            userOrders.forEach(order -> Hibernate.initialize(order.getTickets()));
            return userOrders;
        } catch (Exception e) {
            throw new RuntimeException("Can't get list OrderHistory for User - " + user.getId(), e);
        }
    }
}
