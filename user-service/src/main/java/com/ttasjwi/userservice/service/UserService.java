package com.ttasjwi.userservice.service;

import com.ttasjwi.userservice.service.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    UserDto createUser(UserDto userCreateDto);
    UserDto findUserById(String userId);
    List<UserDto> findAll();

}
