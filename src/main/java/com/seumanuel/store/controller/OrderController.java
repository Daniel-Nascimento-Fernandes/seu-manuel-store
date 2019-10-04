package com.seumanuel.store.controller;


import com.seumanuel.store.model.Order;
import com.seumanuel.store.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @PostMapping
    public Order save(@RequestBody Order order){
        return service.newOrder(order);
    }

    @PutMapping("/{id}")
    public Order cancelOrder(@PathVariable String id, @RequestBody Order order){
        return service.cancelOrder(order);
    }

    @PutMapping("/{id}")
    public Order deliverOrder(@PathVariable String id, @RequestBody Order order){
        return service.deliverOrder(order);
    }

    @PutMapping("/{id}")
    public Order approveOrder(@PathVariable String id, @RequestBody Order order){
        return service.approveOrder(order);
    }

}
