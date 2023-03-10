package ru.username.controler;

import ru.username.entity.Movie;
import ru.username.entity.Ticket;
import ru.username.entity.User;
import ru.username.enumerate.Role;
import ru.username.service.MovieService;
import ru.username.service.TicketService;
import ru.username.service.UserLogService;


import ru.username.service.UserService;
import ru.username.view.MovieView;
import ru.username.view.TicketView;
import ru.username.view.UserView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;

import static java.lang.Integer.parseInt;


public class UserController {
    private static User authUser;
    private final MovieView movieView = new MovieView();
    private final UserView userView = new UserView();

    private final TicketView ticketView = new TicketView();
    private final UserLogService userLogService = new UserLogService();
    private final UserService userService = new UserService();
    private final TicketService ticketService =  new TicketService();
    private final MovieService movieService = new MovieService();
    private final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    /**
     * Метод для регистрации пользователя
     */
    public void createUser() {
        System.out.println("Добро пожаловать для регистрации напишите ваше имя");
        User user;
        try {
            String name = br.readLine();
            System.out.println("укажите пароль");
            String password = br.readLine();
            user = new User(name, password);

            if (Objects.isNull(name) || userService.isUserExists(name)) {
                System.out.println("Указанный вами пользователь существует!");
                return;
            }
            userService.create(user);

            userView.userRegistry(name);
            String message = String.format("Пользователь %s зарегистрировался", name);
            userLogService.addMessage(message, user);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Метод для создание персонала
     *
     * @param authUser
     * @throws IOException
     */
    public void createUserAdmin(User authUser) throws IOException {
        System.out.println("Login");
        String login = br.readLine();
        System.out.println("password");
        String password = br.readLine();
        System.out.println("role");
        Role role = Role.valueOf(br.readLine());
        User user = new User(login, password, role);
        userService.create(user);
        String message = String.format("Пользователь %s зарегистрировал пользователя %s", authUser.getName()
                , user.getName());
        userLogService.addMessage(message, user);
    }

    /**
     * Метод для изменения профильных данных пользователя доступен администратору
     * Метод для возврата билета доступен менеджеру
     *
     * @param authUser
     * @return
     * @throws IOException
     */

    public User updateUser(User authUser) throws IOException {

        User user;
        userView.selectUser(authUser);
        System.out.println("Укажите id пользователя для изменения");

        if (authUser.getRole().equals(Role.ADMIN)) {
            user = selectById(Long.parseLong(br.readLine()));
            String lastName = user.getName();
            System.out.println("Укажите новое имя");
            String newName = br.readLine();
            user.setName(newName);
            System.out.println("Укажите новый пароль");
            user.setPassword(br.readLine());
            userService.updatePassword(user);
            String message = String.format("Имя %s было изменено на %s, пользователем %s", lastName
                    , newName, authUser.getName());
            userLogService.addMessage(message, authUser);

        } else {
            user = selectById(Long.parseLong(br.readLine()));



            String message = String.format("Билет пользователя %s был возвращен менеджером %s ",
                    user.getName(), authUser.getName());
            userLogService.addMessage(message, authUser);

        }
        return user;
    }

    /**
     * Метод возвращает билет пользователя для возврата
     * и пополняет баланс пользоваетеля с помощью метода returnBalanceUser
     *
     * @param user
     * @return
     * @throws IOException
     */
    public Ticket returnTicket(User user) throws IOException {

        System.out.println("Укажите билет для возврата");
        if (ticketView.tiketMenuUser(user)) {


            long number = parseInt(br.readLine());
            if (isNull(ticketService.slectID(number))) {
                return null;
            }
            Ticket removeTicket = ticketService.slectID(number);

            returnBalanceUser(removeTicket, user);

            String message = String.format("Билет на фильм %s был сдан баланс юзер пополнился на %f",
                    removeTicket.getMovie().getName(), removeTicket.getPrice());
            userLogService.addMessage(message, user);

            return removeTicket;
        }
        return null;
    }

    /**
     * Метод для выбора билета и списания баланса с помощью метода balanceUser
     *
     * @param user
     * @return возвращает выбранный билет
     * @throws IOException
     */
    public Ticket paymentTickets(User user) throws IOException {

        try {
            movieView.movieMenu(movieService.select());
            Ticket ticketBuy;
            System.out.println("Выберите предпочитаемый фильм. \n" +
                    "Для выбора фильма введите идентификатор этого фильма:");
            Movie currentMovie = movieService.slectID(Long.parseLong(br.readLine()));
            movieView.selectFreeplacetMenu(currentMovie);
            ticketBuy = ticketService.selectBySeat(Integer.valueOf(br.readLine()), currentMovie);
            if (!balanceBuyUser(ticketBuy, user)) {
                return null;
            }


            String message = String.format("Пользователь %s купил билет на фильм %s со счета списанно %f",
                    user.getName(), ticketBuy.getMovie().getName(), ticketBuy.getPrice());
            userLogService.addMessage(message, user);


            return ticketBuy;
        } catch (NumberFormatException e) {
            System.err.println("Билет указан не верное");
            e.printStackTrace();
        }
        return null;
    }
    /**
     * пополнение баланса за возврат билета
     *
     * @param ticketBuy
     * @param user
     */
    public void returnBalanceUser(Ticket ticketBuy, User user) {
        user.setBalance(user.getBalance() + ticketBuy.getPrice());
        userService.update(user);
    }

    /**
     * списания баланса покупка билета
     *
     * @param ticketBuy
     * @param user
     */
    public boolean balanceBuyUser(Ticket ticketBuy, User user) {
        if (user.getBalance() - ticketBuy.getPrice() < 0) {
            System.out.println("На вашем счете не достаточно средств");
            return false;
        }

        user.setBalance(user.getBalance() - ticketBuy.getPrice());
        userService.update(user);
        return true;
    }


    public User selectById(Long id) {
        return userService.slectID(id);
    }



    /**
     * метод авторизации
     *
     * @return
     */
    public User getAuthUser() {
        System.out.println("Укажите логин");
        try {
            String name = br.readLine();
            System.out.println("Укажте пароль");
            String password = br.readLine();
            authUser = userService.setCerrentUser(name);
            if (isNull(authUser)) {

                return null;
            }
            if (passwordChek(authUser, password)) {
                System.out.println("Авторизация прошла");
                String message = String.format("Пользователь %s вошел в систему",
                        authUser.getName());
                userLogService.addMessage(message, authUser);
            } else {
                System.out.println("Данные указаны не верно");
                getAuthUser();
            }

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Данные указаны не верно");
        }

        return authUser;
    }

    /**
     * удаление пользователя
     * @param user
     */
    public void remove(User user) {

        User u;
        try {
            userView.selectUser(user);
            System.out.println("Укажите id пользователя для удаления");
            long id = Long.parseLong(br.readLine());

            u = selectById(id);
            if (u.getTickets().size() > 0) {
                System.out.println("Пользователь не может быть удален потому что имеется список билетов");

                return;
            }
            userService.delete(u);
            String message = String.format("пользователь %s удалил пользователя %s под id %d ",
                    user.getName(), u.getName(), u.getId());
            userLogService.addMessage(message, user);
        } catch (IOException | NumberFormatException e) {
            System.err.println("не верный формат");
            e.printStackTrace();
        }


    }

    /**
     * проверка пароля
     *
     * @param authUser
     * @param pas
     * @return
     */
    private Boolean passwordChek(User authUser, String pas) {
        Boolean chek = userService.passwordEncoding(pas).equals(authUser.getPassword());
        if (chek) {
            String message = String.format("Пароль пользователя %s прошел проверку авторизации",
                    authUser.getName());
            userLogService.addMessage(message, authUser);
        } else {
            String message = String.format("пользователь %s ввел неверный пароль ",
                    authUser.getName());
            userLogService.addMessage(message, authUser);
        }
        return chek;
    }

    /**
     * проверка обьекта на null
     *
     * @param o
     * @return
     */
    private boolean isNull(Object o) {
        if (Objects.isNull(o)) {
            System.out.println("Данные указаны не верно");
            return true;
        }

        return false;
    }
}
