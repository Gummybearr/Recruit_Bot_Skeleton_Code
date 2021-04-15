package com.gummybearr.jai.domain.user;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(indexes = {@Index(columnList = "userId")})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long userId;
    private String preference;

    public User(Long userId) {
        this.userId = userId;
        this.preference = "";
    }

    public long userId() {
        return this.userId;
    }
}
