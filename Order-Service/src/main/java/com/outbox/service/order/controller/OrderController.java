package com.outbox.service.order.controller;

import com.outbox.service.order.model.Order;
import com.outbox.service.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping(path = "")
    public Order createOrder(@RequestBody Order order) {
        return orderService.createOrder(order);
    }

}
