package com.seumanuel.store.service;

import com.seumanuel.store.model.Order;
import com.seumanuel.store.model.OrderStatus;
import com.seumanuel.store.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository repository;

    public OrderService(OrderRepository repository) {
        this.repository = repository;
    }

    public Order newOrder(Order order){
        return repository.save(order);
    }

    public Order cancelOrder(String id){
        // get objeto
        Optional opt = repository.findById(id);
        if(!opt.isPresent())
            throw new RuntimeException("No order found");

        Order order = (Order)opt.get();
        if(!canBeCanceled(order.getStatus()))
            throw new RuntimeException("Cannot be canceled");

        // alterar estado
        order.setStatus(OrderStatus.CANCELLED);
        return repository.save(order);
    }

    public Order deliverOrder(String id){
        // get objeto
        Optional opt = repository.findById(id);
        if(!opt.isPresent())
            throw new RuntimeException("No order found");

        Order order = (Order)opt.get();
        if(!canBeDelivered(order.getStatus()))
            throw new RuntimeException("Cannot be delivered");

        // alterar estado
        order.setStatus(OrderStatus.DELIVERED);
        return repository.save(order);

    }

    public Order approveOrder(String id){
        // get objeto
        Optional opt = repository.findById(id);
        if(!opt.isPresent())
            throw new RuntimeException("No order found");

        Order order = (Order)opt.get();
        if(!canBeApproved(order.getStatus()))
            throw new RuntimeException("Cannot be approved");

        // alterar estado
        order.setStatus(OrderStatus.APPROVED);
        return repository.save(order);

    }

    private boolean canBeCanceled(OrderStatus status){
        return status.equals(OrderStatus.NEW) | status.equals(OrderStatus.APPROVED);
    }

    private boolean canBeDelivered(OrderStatus status){
        return status.equals(OrderStatus.APPROVED);
    }

    private boolean canBeApproved(OrderStatus status){
        return status.equals(OrderStatus.NEW);
    }
}
