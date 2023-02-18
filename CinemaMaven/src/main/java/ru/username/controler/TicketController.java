package ru.username.controler;

import ru.username.entity.Movie;
import ru.username.entity.Ticket;
import ru.username.entity.User;

import ru.username.service.TicketService;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;

public class TicketController {
    private final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    private final TicketService ticketService = new TicketService();

    /**
     * Записывает билету пользователя
     * @param user
     * @param ticketBuy
     */
    public void buyTicketUser(User user,Ticket ticketBuy){
    if (Objects.isNull(ticketBuy)){
        System.out.println("Проверьте правильность выбранного билета");
        return;
    }
    ticketBuy.setIspurchased(true);
    ticketBuy.setUser(user);
    ticketService.update(ticketBuy);


}
    /**
     * Удаляет пользователя из билета

     */
    public void returnTicket(Ticket ticketReturn){
    if (Objects.isNull(ticketReturn)){
        return;
    }
    ticketReturn.setIspurchased(false);
    ticketReturn.setUser(null);
    ticketService.update(ticketReturn);

    }

    /**
     * показывает свободные билеты на фильм
     * @param movie
     * @return
     */
    public Integer freeTicketCount(Movie movie) {
        Integer count = 0;
        for (Ticket ticket : movie.getTickets()) {
            if (ticket.getIspurchased()) {
                continue;
            }
            count++;
        }
        return count;
    }

    /**
     * Билеты создаются вместе с фильмом
     * @param movie
     */
    public void create(Movie movie) {

        System.out.println("Укажите цену за билет");

        try {
            Double price  = Double.valueOf(br.readLine());

            for (int i = 1; i <= 10; i++) {
                Ticket ticket = new Ticket(i, price, movie);
                ticketService.create(ticket);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


}



