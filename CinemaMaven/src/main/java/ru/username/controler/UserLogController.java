package ru.username.controler;


import ru.username.entity.User;

import ru.username.service.UserLogService;
import ru.username.view.UserLogView;
import ru.username.view.UserView;

import java.util.Scanner;

public class UserLogController {
    private final  UserLogView userLogView = new UserLogView();
    private final UserLogService userLogService = new UserLogService();
    private final   UserView userView = new UserView();
    private final UserController userController = new UserController();
    private final Scanner scanner = new Scanner(System.in);

    /**
     * показывает лог пользователя администратору
     * @param user
     */
    public void showLogAdmin(User user){
        userView.selectUser(user);
        System.out.println("укажите id пользователя для просмотра");
        User u = userController.selectById(scanner.nextLong());
        userLogView.showLog(u);
        String message = String.format("Пользователь %s запросил лог пользователя %s ",
                user.getName(),u.getName());
        userLogService.addMessage(message,user);
    }
}
