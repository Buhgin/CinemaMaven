package ru.username.view;

import de.vandermeer.asciitable.AsciiTable;
import ru.username.entity.User;
import ru.username.entity.UserLog;
import ru.username.service.UserLogService;

import java.io.FileWriter;
import java.io.IOException;

public class UserLogView {
    private final UserLogService userLogModel = new UserLogService();


    /**
     * экспортирует лог в текстовый файл
     * @param user
     */
    public void exportLog(User user){
        String pas ="C:/Users/User/IdeaProjects/CynemaMaven/src/main/java/file/";
        StringBuilder stringBuilder ;
        try (var fileWriter = new FileWriter(String.valueOf(pas + user.getName() + ".txt"))){
           for(UserLog log: user.getUser_logs()){
               stringBuilder= new StringBuilder(String.valueOf(log.getMessage() + log.getSessionDate())+"\n");
               fileWriter.write(String.valueOf(stringBuilder));
               fileWriter.flush();

           }
            String message = String.format("Пользователь %s 'экспортировал лог ",
                    user.getName());
            userLogModel.addMessage(message,user);


        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * показывает лог пользователя
     * @param user
     */
    public void showLog(User user){
        AsciiTable at = new AsciiTable();
        at.addRule();
        at.addRow("Собщение","Дата и время");
        at.addRule();
        for(UserLog userLog: user.getUser_logs()){
            at.addRow(userLog.getMessage(),userLog.getSessionDate());
            at.addRule();
        }
        System.out.println(at.render());
        String message = String.format("Пользователь %s посмотрел лог ",
                user.getName());
        userLogModel.addMessage(message,user);
    }


}
