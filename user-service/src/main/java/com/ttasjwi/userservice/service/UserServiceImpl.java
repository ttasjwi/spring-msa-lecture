package com.ttasjwi.userservice.service;

import com.ttasjwi.userservice.domain.UserEntity;
import com.ttasjwi.userservice.domain.UserRepository;
import com.ttasjwi.userservice.security.SecurityUser;
import com.ttasjwi.userservice.service.dto.OrderDto;
import com.ttasjwi.userservice.service.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public UserDto createUser(UserDto userCreateDto) {

        String userId = UUID.randomUUID().toString();
        String encryptedPassword = passwordEncoder.encode(userCreateDto.getPassword());
        userCreateDto.initUserId(userId);
        userCreateDto.initEncryptedPassword(encryptedPassword);

        UserEntity userEntity = userCreateDto.toEntity();
        userRepository.save(userEntity);

        UserDto returnDto = new UserDto(userEntity);
        return returnDto;
    }

    @Override
    public UserDto findUserById(String userId) {
        UserEntity userEntity = userRepository
                .findByUserId(userId)
                .orElseThrow(() -> new NoSuchElementException("해당하는 사용자를 찾을 수 없습니다."));
        List<OrderDto> orders = new ArrayList<>();

        UserDto returnDto = new UserDto(userEntity);
        returnDto.addOrderDtos(orders);

        return returnDto;
    }

    @Override
    public List<UserDto> findAll() {
        List<UserEntity> userEntities = userRepository.findAll();

        List<UserDto> results = userEntities.stream()
                .map(UserDto::new)
                .collect(Collectors.toList());
        return results;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email));
        return new SecurityUser(userEntity);
    }
}
