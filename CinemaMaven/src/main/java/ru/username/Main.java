package ru.username;


import ru.username.hibernate.HibernateUtil;


public class Main {
    public static void main(String[] args) {

        try {
           HibernateUtil.getSession();
           Mainapp mainapp = new Mainapp();


            mainapp.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    }
