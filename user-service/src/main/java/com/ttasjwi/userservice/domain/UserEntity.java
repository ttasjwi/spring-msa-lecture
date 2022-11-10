package com.ttasjwi.userservice.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "users")
@NoArgsConstructor
@Getter
public class UserEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50, unique = true)
    private String email;

    @Column(nullable = false, length = 50, unique = true)
    private String name;

    @Column(nullable = false, unique = true)
    private String userId;

    @Column(nullable = false)
    private String encryptedPassword;

    @Builder
    public UserEntity(String email, String name, String userId, String encryptedPassword) {
        this.email = email;
        this.name = name;
        this.userId = userId;
        this.encryptedPassword = encryptedPassword;
    }
}
