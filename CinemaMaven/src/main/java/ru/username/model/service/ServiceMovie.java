package ru.username.model.service;

import ru.username.entity.Movie;
import ru.username.model.CRUDModel;
import ru.username.model.MovieModel;

import java.util.List;

public class ServiceMovie {
    private final CRUDModel<Movie> movieModel = new MovieModel();




    public void createMovie(Movie movie) {
        movieModel.create(movie);
    }

    public Movie selectById(Long id) {

        return movieModel.slectID(id);
    }

    public List<Movie> select() {
        return movieModel.select();
    }

    public void delete(Movie movie) {
        movieModel.delete(movie);
    }

    public void update(Movie movie) {
        movieModel.update(movie);
    }
}
