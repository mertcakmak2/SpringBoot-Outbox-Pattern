package com.outbox.service.order.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.outbox.service.order.model.Order;
import com.outbox.service.order.model.Outbox;
import com.outbox.service.order.repository.OrderRepository;
import com.outbox.service.order.repository.OutboxRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OutboxRepository outboxRepository;
    private final ObjectMapper objectMapper;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Order createOrder(Order order) {

        orderRepository.save(order);
        var order_map = objectMapper.convertValue(order, Map.class);
        Outbox outbox = Outbox.builder()
                .event("order_created")
                .eventId(order.getId())
                .payload(order_map)
                .createdAt(new Date())
                .build();

        outboxRepository.save(outbox);
        outboxRepository.delete(outbox);

        //throw new IllegalArgumentException("trigger rollback");
        return order;
    }
}
