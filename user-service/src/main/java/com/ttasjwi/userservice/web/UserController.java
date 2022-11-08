package com.ttasjwi.userservice.web;

import com.ttasjwi.userservice.service.UserService;
import com.ttasjwi.userservice.service.dto.UserDto;
import com.ttasjwi.userservice.vo.Greeting;
import com.ttasjwi.userservice.web.dto.UserCreateRequest;
import com.ttasjwi.userservice.web.dto.UserCreateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/")
public class UserController {

    //    private final Environment env;
    private final Greeting greeting;
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

    @GetMapping("/health-check")
    public String status() {
        return "It's working in USER-SERVICE";
    }

    @GetMapping("/welcome")
    public String welcome() {
        //return env.getProperty("greeting.message");
        return greeting.getMessage();
    }
}
