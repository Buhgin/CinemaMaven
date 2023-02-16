package ru.username.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "movies")
public class Movie {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
@Column(name = "name")
   private String name;
    private LocalDateTime session;
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "movie", cascade = CascadeType.ALL)
    private List<Ticket> tickets;


    public Movie(String name, LocalDateTime session) {
        this.name = name;
        this.session = session;
    }
}
