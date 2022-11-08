package com.ttasjwi.userservice.service;

import com.ttasjwi.userservice.service.dto.UserDto;

public interface UserService {

    UserDto createUser(UserDto userCreateDto);

}
