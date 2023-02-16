package ru.username.controler;


import ru.username.entity.Movie;
import ru.username.entity.Ticket;
import ru.username.model.service.ServiceMovie;
import ru.username.view.MovieView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;

public class MovieController {
   private final MovieView movieView = new MovieView();
    private final ServiceMovie serviceMovie = new ServiceMovie();
    private final TicketController ticketController = new TicketController();
    private final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));


    public void createMovie() {
        System.out.println("Укажите название фильма");
        Movie movie = new Movie();
        try {
            movie.setName(br.readLine());
            System.out.println("Укажите дату сеанса");
            movie.setSession(LocalDateTime.parse(br.readLine()));
            serviceMovie.createMovie(movie);
            ticketController.create(movie);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateMovie() {
        movieView.movieMenu(serviceMovie.select());
         System.out.println("Выберете фильм для обновления");
        try {
            Movie movie = serviceMovie.selectById(Long.valueOf(br.readLine()));
            System.out.println("Укажите новое имя фильма");
            String name = br.readLine();
            System.out.println("Укажите новое время сеанса");
            LocalDateTime localDateTime = LocalDateTime.parse(br.readLine());
            movie.setSession(localDateTime);
            movie.setName(name);
            serviceMovie.update(movie);
        } catch (IOException e) {
            throw new RuntimeException(e);

        }
    }


    public void deleteMovie(){
        movieView.movieMenu(serviceMovie.select());
        Movie movie = null;
        System.out.println("Укажите ID фильма для удаления");
        try {
            movie = serviceMovie.selectById(Long.valueOf(br.readLine()));
            serviceMovie.delete(movie);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
