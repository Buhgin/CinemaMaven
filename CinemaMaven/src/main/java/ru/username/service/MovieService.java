package ru.username.service;

import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.username.entity.Movie;
import ru.username.hibernate.HibernateUtil;

import java.util.List;

public class MovieService implements CRUDService<Movie> {

    @Override
    public void create(Movie movie) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSession()) {
            transaction = session.beginTransaction();
            session.persist(movie);
            transaction.commit();

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }

    }

    @Override
    public List<Movie> select() {
        try {
            CriteriaBuilder criteriaBuilder = HibernateUtil.getSession().getCriteriaBuilder();
            CriteriaQuery<Movie> criteriaQuery = criteriaBuilder.createQuery(Movie.class);
            Root<Movie> movieRoot = criteriaQuery.from(Movie.class);
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
    public Movie slectID(Long id) {
        return HibernateUtil.getSession().get(Movie.class, id);
    }

    @Override
    public void update(Movie movie) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSession()) {
            transaction = session.beginTransaction();
            session.merge(movie);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }


    }

    @Override
    public void delete(Movie movie) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSession()) {
            transaction = session.beginTransaction();
           Movie movie1 = session.get(Movie.class, movie.getId());
            if (movie1 != null) {
                session.remove(movie1);
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
    }

