package com.ttasjwi.userservice.service;

import com.ttasjwi.userservice.repository.UserEntity;
import com.ttasjwi.userservice.repository.UserRepository;
import com.ttasjwi.userservice.service.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserDto createUser(UserDto userCreateDto) {

        String userId = UUID.randomUUID().toString();
        String encryptedPassword = "encrypted_password";
        userCreateDto.setUserId(userId);
        userCreateDto.setEncryptedPassword(encryptedPassword);

        UserEntity userEntity = userCreateDto.toEntity();
        userRepository.save(userEntity);

        UserDto returnDto = new UserDto(userEntity);
        return returnDto;
    }
}
