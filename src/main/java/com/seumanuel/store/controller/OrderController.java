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

    @PutMapping("cancelOrder/{id}")
    public Order cancelOrder(@PathVariable String id){
        return service.cancelOrder(id);
    }

    @PutMapping("deliverOrder/{id}")
    public Order deliverOrder(@PathVariable String id){
        return service.deliverOrder(id);
    }

    @PutMapping("approveOrder/{id}")
    public Order approveOrder(@PathVariable String id){
        return service.approveOrder(id);
    }

}
