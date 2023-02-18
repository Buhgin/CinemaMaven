package ru.username.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import ru.username.enumerate.Role;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "movie_users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private String name;
    private Double balance;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "user",cascade = CascadeType.ALL)
    private List<UserLog> user_logs;
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "user")
    private List<Ticket> tickets;


    public User(String name, String password) {
        this.name = name;
        this.balance = 1000D;
        this.password = password;
        this.role = Role.USER;
    }

    public User(String name, String password, Role role) {
        this.name = name;
        this.balance = 1D;
        this.password = password;
        this.role = role;
    }
}
