package ru.username.model.service;

import lombok.SneakyThrows;
import ru.username.entity.User;
import ru.username.model.UserModel;

import java.security.MessageDigest;
import java.util.List;

public class ServiceUser {



    private final UserModel crudModel = new UserModel();

    public boolean isUserExists(String name) {
        for (User user : select()) {
            if (user.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public void createUser(User user) {
        user.setPassword(passwordEncoding(user.getPassword()));
        crudModel.create(user);
    }

    public User selectById(Long id) {

        return crudModel.slectID(id);
    }

    public List<User> select() {
        return crudModel.select();
    }

    public void delete(User user) {
        crudModel.delete(user);
    }

    public void update(User user) {

        crudModel.update(user);
    }
    public void updatePassword(User user) {
         user.setPassword(passwordEncoding(user.getPassword()));
        crudModel.update(user);
    }


    public User setCerrentUser(String username) {


        for (User u : select()) {
            if (u.getName().equals(username)) {
                return u;
            }

        }
        System.out.println("Пользователь не найден");
        return null;
    }


    @SneakyThrows
    public String passwordEncoding(String password) {
        MessageDigest sha = MessageDigest.getInstance("SHA-1");
        byte[] bytes = sha.digest(password.getBytes());
        StringBuilder builder = new StringBuilder();
        for (byte b : bytes) {
            builder.append(String.format("%02X", b));
        }

        return builder.toString();
    }

}

