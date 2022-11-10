package com.ttasjwi.userservice.web;

import com.ttasjwi.userservice.service.UserService;
import com.ttasjwi.userservice.service.dto.UserDto;
import com.ttasjwi.userservice.web.dto.UserCreateRequest;
import com.ttasjwi.userservice.web.dto.UserCreateResponse;
import com.ttasjwi.userservice.web.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/user-service/")
public class UserController {

    private final Environment env;
    private final UserService userService;


    @PostMapping("/users")
    public ResponseEntity<UserCreateResponse> createUser(@RequestBody UserCreateRequest userCreateRequest) {
        UserDto createUserDto = userCreateRequest.toDto();

        UserDto resultDto = userService.createUser(createUserDto);

        UserCreateResponse response = new UserCreateResponse(resultDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserResponse>> findAllUsers() {
        List<UserDto> userDtos = userService.findAll();

        List<UserResponse> responses = userDtos.stream()
                .map(UserResponse::new)
                .collect(Collectors.toList());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responses);
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<UserResponse> findUserByUserId(@PathVariable String userId) {
        UserDto userDto = userService.findUserById(userId);

        UserResponse response = new UserResponse(userDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @GetMapping("/health-check")
    public String status() {
        String port = env.getProperty("local.server.port");
        log.info("port = {}", port);
        return String.format("It's working in USER-SERVICE on PORT %s", port);
    }

    @GetMapping("/welcome")
    public String welcome() {
        return env.getProperty("greeting.message");
    }
}
