package com.epam.turik.restchat.data.objects.user;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "user", schema = "public")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    @Column(name = "user_status")
//    @Enumerated(EnumType.STRING)
    private String status;
    private String email;
    private String timezone;
    private String language;
    @Column(name = "deletion_date")
    private Timestamp deletionDate;
    @Column(name = "chat_permission")
    private String chatPermission;
    private String ip;

}
