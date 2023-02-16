package ru.username.entity;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor

@Entity
@Table(name = "user_logs")
public class UserLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name ="session_date",updatable = false)
    private LocalDateTime sessionDate;
    @Column(length = 1000)
    private String message;
   @ManyToOne()
    @JoinColumn(name = "user_id")
    private User user;

    public UserLog( String message, User users) {
        this.sessionDate = LocalDateTime.now();;
        this.message = message;
        this.user = users;
    }
}
