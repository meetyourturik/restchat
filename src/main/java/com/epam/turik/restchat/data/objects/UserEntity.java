package com.epam.turik.restchat.data.objects;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "user", schema = "public")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Getter
    @Setter
    private Long id;
    @Getter
    @Setter
    private String username;
    @Getter
    @Setter
    @Column(name = "user_status")
    @Enumerated(EnumType.STRING)
    private UserStatus status;

    public User() {

    }

    public User(String username, UserStatus status) {
        this.username = username;
        this.status = status;
    }
}
