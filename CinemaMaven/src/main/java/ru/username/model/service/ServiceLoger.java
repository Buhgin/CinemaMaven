package ru.username.model.service;


import ru.username.entity.User;
import ru.username.entity.UserLog;
import ru.username.model.CRUDModel;
import ru.username.model.UserLogModel;

public class ServiceLoger {
    private final CRUDModel<UserLog>  logCRUDModel = new UserLogModel();
    public  void addMessage(String message, User user){
        UserLog userLog = new UserLog(message,user);
       logCRUDModel.create(userLog);
    }

}
