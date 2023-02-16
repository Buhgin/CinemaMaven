package ru.username.view;

import de.vandermeer.asciitable.AsciiTable;
import ru.username.entity.User;
import ru.username.enume.Role;
import ru.username.model.service.ServiceLoger;
import ru.username.model.service.ServiceUser;

public class UserView {
     ServiceUser serviceUser = new ServiceUser();
    ServiceLoger serviceLoger = new ServiceLoger();
    public void userRegistry(String name){
        String congrats = String.format(
                "Поздравляем! Вы успешно зарегистрировались в системе!\n" +
                        "-----------------------------------------------------\n" +
                        "Данные для входа в приложение:\n" +
                        "Ваш логин: %s, пароль: (указанный при регистрации)\n" +
                        "-----------------------------------------------------\n", name
        );
        System.out.println(congrats);

    }
    public void getProfile(User user){

        System.out.printf("Ваш логин: %s, пароль (указанный при регистрации) баланс %f"
                ,user.getName(),user.getBalance());
        System.out.println();
        String message = String.format("Пользователь %s посмотрел профиль",
                user.getName());
        serviceLoger.addMessage(message,user);

    }
    public Double getBalance(User user){
        String message = String.format("Пользователь %s посмотрел баланс",
                user.getName());
        serviceLoger.addMessage(message,user);
        return user.getBalance();
    }

    /**
     * Показываеь таблицу пользователей с ролью юзер
     * @param user
     */
    public void selectUser(User user){
        AsciiTable at = new AsciiTable();
        at.addRule();
        at.addRow("Логин","id");
        for(User u: serviceUser.select()){
            if(u.getRole()== Role.USER){
            at.addRow(u.getName(),u.getId());
            at.addRule();}

        }
        System.out.println(at.render());
        String message = String.format("Пользователь %s , роль %s, посмотрел список пользователей ",
                user.getName(),user.getRole());
        serviceLoger.addMessage(message,user);
    }
}
