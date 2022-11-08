package com.ttasjwi.userservice.service.dto;


import com.ttasjwi.userservice.repository.UserEntity;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UserDto {

    private String userId;
    private String name;
    private String email;
    private String password;
    private String encryptedPassword;
    private LocalDate createdAt;

    @Builder
    public UserDto(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public UserDto(UserEntity userEntity) {
        this.userId = userEntity.getUserId();
        this.email = userEntity.getEmail();
        this.name = userEntity.getName();
        this.encryptedPassword = userEntity.getEncryptedPassword();
    }

    public UserEntity toEntity() {
        return UserEntity.builder()
                .userId(userId)
                .name(name)
                .email(email)
                .encryptedPassword(encryptedPassword)
                .build();
    }
}
