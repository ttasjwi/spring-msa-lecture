package com.ttasjwi.orderservice.web;

import com.ttasjwi.orderservice.service.OrderService;
import com.ttasjwi.orderservice.service.dto.OrderDto;
import com.ttasjwi.orderservice.web.dto.OrderCreateRequest;
import com.ttasjwi.orderservice.web.dto.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/order-service")
public class OrderController {

    private final OrderService orderService;

    @PostMapping(value = "/{userId}/orders")
    public ResponseEntity<OrderResponse> createOrder(
            @PathVariable("userId") String userId,
            @RequestBody OrderCreateRequest orderRequest) {

        OrderDto orderDetails = orderRequest.toDto();
        orderDetails.setUserId(userId);

        OrderDto resultDto = orderService.createOrder(orderDetails);

        OrderResponse response = new OrderResponse(resultDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/orders/{orderId}")
    public ResponseEntity<OrderResponse> findOrderByOrderId(@PathVariable("orderId") String orderId) {

        OrderDto orderDto = orderService.findOrderByOrderId(orderId);

        OrderResponse orderResponse = new OrderResponse(orderDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(orderResponse);
    }

    @GetMapping("/{userId}/orders")
    public ResponseEntity<List<OrderResponse>> findOrdersByUserId(@PathVariable("userId") String userId) {

        List<OrderDto> orderDtos = orderService.findOrdersByUserId(userId);

        List<OrderResponse> orderResponses = orderDtos.stream()
                .map(OrderResponse::new)
                .collect(Collectors.toList());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(orderResponses);
    }

}
