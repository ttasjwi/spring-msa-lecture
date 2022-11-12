package com.ttasjwi.orderservice.service;

import com.ttasjwi.orderservice.domain.OrderEntity;
import com.ttasjwi.orderservice.domain.OrderRepository;
import com.ttasjwi.orderservice.service.dto.OrderDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Override
    public OrderDto createOrder(OrderDto orderDetails) {
        String orderId = UUID.randomUUID().toString();
        orderDetails.setOrderId(orderId);

        OrderEntity orderEntity = orderDetails.toEntity();
        orderRepository.save(orderEntity);

        OrderDto result = new OrderDto(orderEntity);
        return result;
    }

    @Override
    public OrderDto findOrderByOrderId(String orderId) {
        OrderEntity orderEntity = orderRepository.findByOrderId(orderId)
                .orElseThrow(() -> new NoSuchElementException("해당하는 주문을 찾을 수 없습니다."));

        OrderDto returnDto = new OrderDto(orderEntity);
        return returnDto;
    }

    @Override
    public List<OrderDto> findOrdersByUserId(String userId) {
        List<OrderEntity> orderEntities = orderRepository.findByUserId(userId);

        List<OrderDto> returnDtos = orderEntities.stream()
                .map(OrderDto::new)
                .collect(Collectors.toList());

        return returnDtos;
    }
}
