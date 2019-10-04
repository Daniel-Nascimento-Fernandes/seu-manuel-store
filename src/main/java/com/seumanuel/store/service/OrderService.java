package com.seumanuel.store.service;

import com.seumanuel.store.model.Order;
import com.seumanuel.store.model.OrderStatus;
import com.seumanuel.store.repository.OrderRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private final OrderRepository repository;

    public OrderService(OrderRepository repository) {
        this.repository = repository;
    }

    public Order newOrder(Order order){
        return repository.save(order);
    }

    public Order cancelOrder(Order order){
        if(!canBeCanceled(order.getStatus()))
            throw new RuntimeException("Cannot be canceled");
        return repository.save(order);
    }

    public Order deliverOrder(Order order){
        if(!canBeDelivered(order.getStatus()))
            throw new RuntimeException("Cannot be delivered");
        return repository.save(order);

    }

    public Order approveOrder(Order order){
        if(!canBeApproved(order.getStatus()))
            throw new RuntimeException("Cannot be approved");
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
