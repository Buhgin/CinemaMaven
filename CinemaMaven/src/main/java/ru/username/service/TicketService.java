package ru.username.service;

import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.username.entity.Movie;
import ru.username.entity.Ticket;
import ru.username.hibernate.HibernateUtil;

import java.util.List;

public class TicketService implements CRUDService<Ticket> {
    @Override
    public void create(Ticket ticket) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSession()) {
            transaction = session.beginTransaction();
            session.persist(ticket);
            transaction.commit();

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }

    }

    @Override
    public List<Ticket> select() {
        try {
            CriteriaBuilder criteriaBuilder = HibernateUtil.getSession().getCriteriaBuilder();
            CriteriaQuery<Ticket> criteriaQuery = criteriaBuilder.createQuery(Ticket.class);
            Root<Ticket> ticketRoot = criteriaQuery.from(Ticket.class);
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
    public Ticket slectID(Long id) {
        return HibernateUtil.getSession().get(Ticket.class, id);
    }

    @Override
    public void update(Ticket ticket) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSession()) {
            transaction = session.beginTransaction();
            session.merge(ticket);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Ticket ticket) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSession()) {
            transaction = session.beginTransaction();
            Ticket ticket1 = session.get(Ticket.class, ticket.getId());
            if (ticket1 != null) {
                session.remove(ticket1);
                System.out.println("ticket is delete");
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }


    }
    public Ticket selectBySeat(Integer seat, Movie curentTicket){
        for(Ticket t : curentTicket.getTickets()){
            if(t.getSeat().equals(seat)){
                return t;
            }
        }
        System.out.println("Билет не найден");
        return null;
    }
}
