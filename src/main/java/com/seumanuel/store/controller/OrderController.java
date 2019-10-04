package com.seumanuel.store.controller;


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
    public Product cancelOrder(@PathVariable String id, @RequestBody Order order){
        return service.cancelOrder(order);
    }

    @PutMapping("/{id}")
    public Product deliverOrder(@PathVariable String id, @RequestBody Order order){
        return service.deliverOrder(order);
    }

    @PutMapping("/{id}")
    public Product approveOrder(@PathVariable String id, @RequestBody Order order){
        return service.approveOrder(order);
    }

}
