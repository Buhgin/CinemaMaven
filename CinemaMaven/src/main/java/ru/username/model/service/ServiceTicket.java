package ru.username.model.service;

import ru.username.entity.Movie;
import ru.username.entity.Ticket;
import ru.username.model.CRUDModel;
import ru.username.model.TicketModel;

import java.util.List;


public class ServiceTicket {
    private final CRUDModel<Ticket> ticketCRUDModel = new TicketModel();
    public void create(Ticket ticket) {
    ticketCRUDModel.create(ticket);
}

    public Ticket selectById(Long id) {


        return ticketCRUDModel.slectID(id);
    }

    public List<Ticket> select() {
        return ticketCRUDModel.select();
    }





    public void update(Ticket ticket) {
        ticketCRUDModel.update(ticket);
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
