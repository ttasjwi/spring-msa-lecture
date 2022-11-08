package com.ttasjwi.userservice.web.dto;

import com.ttasjwi.userservice.service.dto.UserDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@RequiredArgsConstructor
@ToString(of = {"email", "password", "name"})
public class UserCreateRequest {

    @NotNull(message = "Email cannot be null")
    @Size(min=2, message = "Email not be less than two characters")
    @Email
    private final String email;

    @NotNull(message = "Password cannot be null")
    @Size(min=8, message = "Password must be equal or greater than 8 characters and less than 16 characters")
    private final String password;

    @NotNull(message = "Name cannot be null")
    @Size(min = 2, message = "Name not be less than two characters")
    private final String name;

    public UserDto toDto() {
        return UserDto.builder()
                .email(email)
                .password(password)
                .name(name)
                .build();
    }
}
