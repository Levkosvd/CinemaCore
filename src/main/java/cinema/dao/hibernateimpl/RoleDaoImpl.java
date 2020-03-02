package cinema.dao.hibernateimpl;

import cinema.dao.RoleDao;
import cinema.model.Role;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

@Repository
public class RoleDaoImpl implements RoleDao {
    final SessionFactory sessionFactory;

    public RoleDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Role add(Role entity) {
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
            throw new RuntimeException("Can't insert Role entity", e);
        }
    }

    @Override
    public Role findById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Role.class,id);
        } catch (Exception e) {
            throw new RuntimeException("Can't find Role by id -" + id, e);
        }
    }

    @Override
    public Role findByRoleName(String roleName) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Role> roleCriteriaQuery =
                    criteriaBuilder.createQuery(Role.class);
            Root<Role> roleRoot =
                    roleCriteriaQuery.from(Role.class);
            roleCriteriaQuery
                    .select(roleRoot)
                    .where(criteriaBuilder.and(criteriaBuilder
                            .equal(roleRoot.get("name"), roleName)));
            return session.createQuery(roleCriteriaQuery).uniqueResult();
        } catch (Exception e) {
            throw new RuntimeException("Can't find Role by name -" + roleName, e);
        }
    }
}
