package com.ttasjwi.userservice.web.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ttasjwi.userservice.service.dto.UserDto;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL) // null인 것은 응답하지 않겠다.
public class UserResponse {
    
    private String email;
    private String name;
    private String userId;
    
    private final List<OrderResponse> orders = new ArrayList<>();
    
    public UserResponse(UserDto userDto) {
        initUserEmail(userDto);
        initName(userDto);
        initUserId(userDto);
        initOrders(userDto);
    }

    private void initUserId(UserDto userDto) {
        this.userId = userDto.getUserId();
    }

    private void initName(UserDto userDto) {
        this.name = userDto.getName();
    }

    private void initUserEmail(UserDto userDto) {
        this.email = userDto.getEmail();
    }

    private void initOrders(UserDto userDto) {
        List<OrderResponse> orders = userDto.getOrders()
                .stream()
                .map(OrderResponse::new)
                .collect(Collectors.toList());
        this.orders.addAll(orders);
    }
}
