package ru.username.view;

import de.vandermeer.asciitable.AsciiTable;
import ru.username.controler.TicketController;
import ru.username.entity.Movie;
import ru.username.entity.Ticket;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class MovieView {
   private final TicketController ticketController =new TicketController();

    /**
     * показывает доступные фильмы
     * @param movieList
     */
    public void movieMenu(List<Movie> movieList){
        AsciiTable at = new AsciiTable();
        at.addRule();
        at.addRow("Id","Название фильма","дата и время","Количество доступных билетов");

        at.addRule();
        for(Movie m : movieList){
            at.addRow( m.getId(),m.getName(),m.getSession(), ticketController.freeTicketCount(m));
            at.addRule();

        }
        System.out.println(at.render());

    }

    /**
     * показывает свободные места фильма
     * @param movie
     */
    public void selectFreeplacetMenu(Movie movie){
        if(Objects.isNull(movie)){
            System.err.println("Фильм указан не верно ");
            return;
        }
        movie.getTickets().sort(Comparator.comparingInt(Ticket::getSeat));
        AsciiTable at = new AsciiTable();
        at.addRule();
        at.addRow("Место", "цена", "название фильма");
        for (int i = 0; i <movie.getTickets().size() ; i++) {
               Ticket  ticket = movie.getTickets().get(i);
            if (ticket.getIspurchased()){
                continue;
            }
            at.addRow(ticket.getSeat(),ticket.getPrice(),movie.getName());
            at.addRule();
        }
        System.out.println(at.render());
        System.out.printf("\nСписок доступных билетов для фильма -%s",movie.getName());

    }
}
