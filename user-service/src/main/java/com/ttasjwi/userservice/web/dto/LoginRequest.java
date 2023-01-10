package com.ttasjwi.userservice.web.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class LoginRequest {

    @NotNull(message = "Email cannot be null")
    @Size(min = 2, message = "Email not be less than two character")
    @Email
    private String email;

    @NotNull(message = "Password cannot be null")
    @Size(min = 8, message = "Email must be equals or greater than eight character")
    private String password;

}
