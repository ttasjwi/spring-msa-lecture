package com.ttasjwi.userservice.service.dto;


import com.ttasjwi.userservice.domain.UserEntity;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
public class UserDto {

    private String userId;
    private String name;
    private String email;
    private String password;
    private String encryptedPassword;
    private final List<OrderDto> orders = new ArrayList<>();
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

    public void initUserId(String userId) {
        this.userId = userId;
    }

    public void initEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    public void addOrderDtos(List<OrderDto> orders) {
        this.orders.addAll(orders);
    }
}
