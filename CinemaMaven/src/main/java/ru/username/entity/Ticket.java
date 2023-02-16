package ru.username.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "tickets")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer seat;
    private Double price;
    @Column(name = "is_purchased")
    private Boolean ispurchased;
    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne()
    @JoinColumn(name = "movie_id")
    private Movie movie;


    public Ticket(Integer seat, Double price, User user, Movie movie) {
        this.seat = seat;
        this.price = price;
        this.user = user;
        this.movie = movie;
        this.ispurchased = true;
    }

    public Ticket(Integer seat, Double price, Movie movie) {
        this.seat = seat;
        this.price = price;
        this.movie = movie;
        this.ispurchased = false;
    }
}
