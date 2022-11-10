package com.ttasjwi.userservice.service;

import com.ttasjwi.userservice.service.dto.UserDto;

import java.util.List;

public interface UserService {

    UserDto createUser(UserDto userCreateDto);
    UserDto findUserById(String userId);
    List<UserDto> findAll();

}
