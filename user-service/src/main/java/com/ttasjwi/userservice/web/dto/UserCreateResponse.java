package com.ttasjwi.userservice.web.dto;

import com.ttasjwi.userservice.service.dto.UserDto;
import lombok.Data;

@Data
public class UserCreateResponse {

    private String email;
    private String name;
    private String userId;

    public UserCreateResponse(UserDto userCreateDto) {
        this.email = userCreateDto.getEmail();
        this.userId = userCreateDto.getUserId();
        this.name = userCreateDto.getName();
    }

}
