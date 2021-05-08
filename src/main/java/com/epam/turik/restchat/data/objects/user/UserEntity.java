package com.epam.turik.restchat.data.objects.user;

import com.epam.turik.restchat.model.objects.user.ChatPermission;
import com.epam.turik.restchat.model.objects.user.UserStatus;
import com.vladmihalcea.hibernate.type.basic.Inet;
import com.vladmihalcea.hibernate.type.basic.PostgreSQLEnumType;
import com.vladmihalcea.hibernate.type.basic.PostgreSQLInetType;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@Entity
@Table(name = "user", schema = "public")
@TypeDefs({
        @TypeDef(
                name = "pgEnum",
                typeClass = PostgreSQLEnumType.class
        ),
        @TypeDef(
                name = "pgInet",
                typeClass = PostgreSQLInetType.class
        )
})
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    @Column(name = "user_status")
    @Enumerated(EnumType.STRING)
    @Type(type = "pgEnum")
    private UserStatus status;
    private String email;
    private String timezone;
    private String language;
    @Column(name = "deletion_date")
    private Timestamp deletionDate;
    @Column(name = "chat_permission")
    @Enumerated(EnumType.STRING)
    @Type(type = "pgEnum")
    private ChatPermission chatPermission;
    @Column(columnDefinition = "inet")
    @Type(type = "pgInet")
    private Inet ip;

}
