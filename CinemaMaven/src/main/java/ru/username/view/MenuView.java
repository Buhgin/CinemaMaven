package ru.username.view;

import de.vandermeer.asciitable.AsciiTable;


public class MenuView {



    public void printAuthMenuOptions() {
        AsciiTable at = new AsciiTable();
        at.addRule();
        at.addRow("Здравствуйте! Для продолжения работы в приложении Вам необходимо авторизоваться. Выберите соответствующий пункт: ");
        at.addRule();
        at.addRow("1 - Регистрация");
        at.addRule();
        at.addRow("2 - Вход");
        at.addRule();
        System.out.println(at.render());
    }

    public void printMainMenuUser(){
        System.out.println();
        AsciiTable at = new AsciiTable();
        at.addRule();
        at.addRow("Главное меню - Выберите соответствующий пункт меню ");
        at.addRule();
        at.addRow("1 - Просмотреть список доступных фильмов");
        at.addRule();
        at.addRow("2 - Купить билет");
        at.addRule();
        at.addRow("3 - Вернуть приобретенный билет ");
        at.addRule();
        at.addRow("4 - Просмотреть список приобретенных билетов ");
        at.addRule();
        at.addRow("5 - Проверить лицевой счет ");
        at.addRule();
        at.addRow("6 - Посмотреть профиль ");
        at.addRule();
        at.addRow("7 - Посмотреть лог ");
        at.addRule();
        at.addRow("8 - Экспортировать лог ");
        at.addRule();
        at.addRow("9 -  Выйти из системы ");
        at.addRule();
        at.addRow("10 - Выход из программы");
        at.addRule();
        System.out.println(at.render());
    }
    public void printMainMenuAdmin(){
            System.out.println();
            AsciiTable at = new AsciiTable();
            at.addRule();
            at.addRow("Главное меню - Выберите соответствующий пункт меню ");
            at.addRule();
            at.addRow("1 - Просмотреть список пользователей");
            at.addRule();
            at.addRow("2 - Изменить пользователя");
            at.addRule();
            at.addRow("3 - Удалить пользователя ");
            at.addRule();
            at.addRow("4 - Просмотреть список фильмов ");
            at.addRule();
            at.addRow("5 - Изменить фильм ");
            at.addRule();
            at.addRow("6 - Удалить фильм");
            at.addRule();
            at.addRow("7 - Посмотреть логи системы ");
            at.addRule();
            at.addRow("8 -  Выйти из системы ");
            at.addRule();
            at.addRow("9 - Выход из программы");
            at.addRule();
            at.addRow("10 - регистрация пеосонала");
            System.out.println(at.render());
    } public void printMainMenuManager(){

        System.out.println();
        AsciiTable at = new AsciiTable();
        at.addRule();
        at.addRow("Главное меню - Выберите соответствующий пункт меню ");
        at.addRule();
        at.addRow("1 -  Просмотреть список фильмов");
        at.addRule();
        at.addRow("2 - Изменить фильм ");
        at.addRule();
        at.addRow("3 -  Создать фильм ");
        at.addRule();
        at.addRow("4 - Оформить возврат билета ");
        at.addRule();
        at.addRow("5 - Выход из системы");
        at.addRule();
        at.addRow("6 - Выход из программы");
        at.addRule();
        System.out.println(at.render());
    }






}
