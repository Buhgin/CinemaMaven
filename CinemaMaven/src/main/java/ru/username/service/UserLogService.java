package ru.username.service;

import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.username.entity.User;
import ru.username.entity.UserLog;
import ru.username.hibernate.HibernateUtil;

import java.util.List;

public class UserLogService implements CRUDService<UserLog> {
    @Override
    public void create(UserLog objectClass) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSession()) {
            transaction = session.beginTransaction();
            session.persist(objectClass);
            transaction.commit();

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }

    }

    @Override
    public List<UserLog> select() {
        try {
            CriteriaBuilder criteriaBuilder = HibernateUtil.getSession().getCriteriaBuilder();
            CriteriaQuery<UserLog> criteriaQuery = criteriaBuilder.createQuery(UserLog.class);
            Root<UserLog> movieRoot = criteriaQuery.from(UserLog.class);
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
    public UserLog slectID(Long id) {
        return HibernateUtil.getSession().get(UserLog.class, id);
    }

    @Override
    public void update(UserLog objectClass) {
            Transaction transaction = null;
            try (Session session = HibernateUtil.getSession()) {
                transaction = session.beginTransaction();
                session.merge(objectClass);
                transaction.commit();
            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
                e.printStackTrace();
            }

        }

        @Override
        public void delete(UserLog userLog){
            Transaction transaction = null;
            try (Session session = HibernateUtil.getSession()) {
                transaction = session.beginTransaction();
                UserLog user1 = session.get(UserLog.class, userLog.getId());
                if (user1 != null) {
                    session.remove(user1);
                    System.out.println("movie is delete");
                }
                transaction.commit();
            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
                e.printStackTrace();
            }
        }
    public  void addMessage(String message, User user){
        UserLog userLog = new UserLog(message,user);
        create(userLog);
    }
    }
