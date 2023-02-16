package ru.username;

import ru.username.controler.UserLogController;
import ru.username.view.*;
import ru.username.controler.MovieController;
import ru.username.controler.TicketController;
import ru.username.controler.UserController;
import ru.username.entity.User;
import ru.username.model.service.ServiceMovie;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.Scanner;

public class Mainapp {
    private final UserLogController userLogController = new UserLogController();
    private final TicketController ticketController = new TicketController();
    private final TicketView ticketView = new TicketView();
    private final MovieView movieView = new MovieView();
    private final ServiceMovie serviceMovie = new ServiceMovie();
    private final UserLogView userLogView = new UserLogView();
    private final MenuView menuView = new MenuView();
    private final UserView userView = new UserView();
    private final MovieController movieController = new MovieController();
    private final UserController userController = new UserController();
    private final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static User authUser;

    /**
     * Метод собирает приложение
     */
    public void start() {
        try {
            startMenu();
            switch (authUser.getRole()) {
                case USER -> mainMenuUser();
                case ADMIN -> mainMenuAdmin();
                case MANAGER -> mainMenuManager();
            }
        } catch (RuntimeException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Стартовое мень регистрации авторизации
     * @throws IOException
     */
    public void startMenu() throws IOException {

        int i;
        do {
            menuView.printAuthMenuOptions();
            i = Integer.parseInt(br.readLine());
            switch (i) {
                case 1 -> userController.createUser();
                case 2 -> authUser = userController.getAuthUser();
                default -> System.out.println("Выберете пункт из меню");

            }
        } while (Objects.isNull(authUser));

        System.out.println(authUser.getName());
    }

    /**
     * меню менеджера
     * @throws IOException
     */
    private void mainMenuManager() throws IOException {
        int i;
        do {
            menuView.printMainMenuManager();
            i = Integer.parseInt(br.readLine());
            switch (i) {
                case 1 -> movieView.movieMenu(serviceMovie.select());
                case 2 -> movieController.updateMovie();
                case 3 -> movieController.createMovie();
                case 4 -> ticketController.returnTicket(
                        userController.returnTicket(
                                userController.updateUser(authUser)));

                case 5 -> exitSystem();
                case 6 -> {
                    System.exit(0);
                }
                default -> System.out.println("Выберете пункт из меню");
            }
            System.out.println("\n Для получения меню нажмите любую клавишу");
            Scanner scanner = new Scanner(System.in);
            String s = scanner.nextLine();

        } while (true);
    }



    /**
     * меню юзера
     * @throws IOException
     */
    public void mainMenuUser() throws IOException {
        int i;
        do {
            menuView.printMainMenuUser();
            i = Integer.parseInt(br.readLine());
            switch (i) {
                case 1 -> movieView.movieMenu(serviceMovie.select());
                case 2 -> ticketController.buyTicketUser(authUser, userController.paymentTickets(authUser));
                case 3 ->  ticketController.returnTicket(userController.returnTicket(authUser));
                case 4 -> ticketView.tiketMenuUser(authUser);
                case 5 -> System.out.printf(" Ваш баланс %f", userView.getBalance(authUser));
                case 6 -> userView.getProfile(authUser);
                case 7 -> userLogView.showLog(authUser);
                case 8 -> userLogView.exportLog(authUser);
                case 9 -> exitSystem();
                case 10 -> {
                    System.exit(0);
                }
                default -> System.out.println("Выберете пункт из меню");
            }
            System.out.println("\n Для получения меню нажмите любую клавишу");
            Scanner scanner = new Scanner(System.in);
            String s = scanner.nextLine();
        } while (true);
    }



    /**
     * меню администратора
     * @throws IOException
     */
    public void mainMenuAdmin() throws IOException {
        int i;
        do {
            menuView.printMainMenuAdmin();
            i = Integer.parseInt(br.readLine());
            switch (i) {
                case 1 -> userView.selectUser(authUser);
                case 2 -> userController.updateUser(authUser);
                case 3 -> userController.remove(authUser);
                case 4 -> movieView.movieMenu(serviceMovie.select());
                case 5 -> movieController.updateMovie();
                case 6 -> movieController.deleteMovie();
                case 7 -> userLogController.showLogAdmin(authUser);
                case 8 -> exitSystem();
                case 9 -> {
                    System.exit(0);
                }
                case 10 -> userController.createUserAdmin(authUser);
                default -> System.out.println("Выберете пункт из меню");
            }
            System.out.println("\n Для получения меню нажмите любую клавишу");
            Scanner scanner = new Scanner(System.in);
            String s = scanner.nextLine();
        } while (true);
    }
    private void exitSystem() {
        authUser=null;

        start();
    }
}