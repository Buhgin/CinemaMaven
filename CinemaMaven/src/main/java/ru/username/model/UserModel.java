package ru.username.model;


import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.username.entity.User;
import ru.username.hibernate.HibernateUtil;

import java.util.List;

public class UserModel implements CRUDModel<User>{
    @Override
    public void create(User user) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSession()) {
            transaction = session.beginTransaction();
            session.persist(user);
            transaction.commit();

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
    @Override
    public List<User> select() {
        try {
            CriteriaBuilder criteriaBuilder = HibernateUtil.getSession().getCriteriaBuilder();
            CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
            Root<User> userRoot = criteriaQuery.from(User.class);
            Query query = HibernateUtil.getSession().createQuery(criteriaQuery);
            return query.getResultList();

        } catch (Exception e) {
            if (!HibernateUtil.getSession().isConnected()) {
                HibernateUtil.getSession().beginTransaction().rollback();
            }
            e.printStackTrace();
            return null;
        }
    }



    @Override
    public User slectID(Long id) {
        return HibernateUtil.getSession().get(User.class, id);
    }

    @Override
    public void update(User user) {


        Transaction transaction = null;
        try (Session session = HibernateUtil.getSession()) {
            transaction = session.beginTransaction();
            session.merge(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }

    }

    @Override
    public void delete(User user) {
        String name = user.getName();
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSession()) {
            transaction = session.beginTransaction();
            User user1 = session.get(User.class, user.getId());
            if (user1 != null) {
                session.remove(user1);
                System.out.printf("user %s is delete",name);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }


}
