package ru.username.view;

import de.vandermeer.asciitable.AsciiTable;
import ru.username.entity.Ticket;
import ru.username.entity.User;
import ru.username.service.TicketService;


import java.util.ArrayList;
import java.util.List;

public class TicketView {

    /**
     * показывает доступные билеты у пользователя если билетов нет возвращает fals если есть тру
     * @param user
     * @return
     */
    public boolean tiketMenuUser(User user){
        TicketService ticketModel = new TicketService();
     List<Ticket> list = new ArrayList<>();
        AsciiTable at = new AsciiTable();
        at.addRule();
        at.addRow("Фильм","цена","место","номер для выбора билета");
        at.addRule();
        for (int i = 0; i < ticketModel.select().size(); i++) {
            Ticket t = ticketModel.select().get(i);
            if (t.getIspurchased() && user.getId().equals(t.getUser().getId())) {
           list.add(t);
            at.addRow(t.getMovie().getName(), t.getPrice(),t.getSeat(), t.getId());
            at.addRule();
            }

        }
        if(list.size()==0){
            System.out.println("У пользователя нет билетов");
            return false;
        }
        System.out.println(at.render());
    return  true;}

}
