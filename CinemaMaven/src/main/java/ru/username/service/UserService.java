package ru.username.service;


import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import lombok.SneakyThrows;
import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.username.entity.User;
import ru.username.hibernate.HibernateUtil;

import java.security.MessageDigest;
import java.util.List;

public class UserService implements CRUDService<User> {
    @Override
    public void create(User user) {
        user.setPassword(passwordEncoding(user.getPassword()));
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
    public boolean isUserExists(String name) {
        for (User user : select()) {
            if (user.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }
    public void updatePassword(User user) {
        user.setPassword(passwordEncoding(user.getPassword()));
        update(user);
    }
    public User setCerrentUser(String username) {
        for (User u : select()) {
            if (u.getName().equals(username)) {
                return u;
            }

        }
        System.out.println("Пользователь не найден");
        return null;
    }
    @SneakyThrows
    public String passwordEncoding(String password) {
        MessageDigest sha = MessageDigest.getInstance("SHA-1");
        byte[] bytes = sha.digest(password.getBytes());
        StringBuilder builder = new StringBuilder();
        for (byte b : bytes) {
            builder.append(String.format("%02X", b));
        }

        return builder.toString();
    }
}
