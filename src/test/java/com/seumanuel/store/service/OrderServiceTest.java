package com.seumanuel.store.service;


import com.seumanuel.store.model.Order;
import com.seumanuel.store.model.OrderStatus;
import com.seumanuel.store.repository.OrderRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static com.seumanuel.store.helper.OrderHelper.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class OrderServiceTest {

    @Mock
    private OrderRepository repository;

    private OrderService service;

    @Rule
    public ExpectedException exceptionExpect = ExpectedException.none();

    @Before
    public void setUp() {
        this.service = new OrderService(repository);
    }


    @Test
    public void testComputerNewOrderSuccess() {
        //setup
        given(repository.save(any(Order.class))).willAnswer(defaultOrderAnswer("1234"));
        Order orderToSave = newOrder("1234");

        //exec
        Order ret = service.newOrder(orderToSave);

        //assert
        ArgumentCaptor<Order> argument = ArgumentCaptor.forClass(Order.class);

        verify(repository, atLeastOnce()).save(argument.capture());


        Assert.assertEquals("1234", ret.getId());
        Assert.assertEquals(OrderStatus.NEW, ret.getStatus());

    }


    @Test
    public void testComputerCancelOrderSuccess() {
        //setup
        given(repository.save(any(Order.class))).willAnswer(defaultOrderAnswer("1234",OrderStatus.CANCELLED));
        given(repository.findById("1234")).willReturn(Optional.of(newOrder("1234")));

        //exec
        Order ret = service.cancelOrder("1234");

        //assert
        ArgumentCaptor<Order> argument = ArgumentCaptor.forClass(Order.class);

        verify(repository, atLeastOnce()).save(argument.capture());


        Assert.assertEquals("1234", ret.getId());
        Assert.assertEquals(OrderStatus.CANCELLED, ret.getStatus());

    }

    @Test
    public void testComputerApproveOrderSuccess() {
        //setup
        given(repository.save(any(Order.class))).willAnswer(defaultOrderAnswer("1234",OrderStatus.APPROVED));
        given(repository.findById("1234")).willReturn(Optional.of(newOrder("1234")));

        //exec
        Order ret = service.approveOrder("1234");

        //assert
        ArgumentCaptor<Order> argument = ArgumentCaptor.forClass(Order.class);

        verify(repository, atLeastOnce()).save(argument.capture());


        Assert.assertEquals("1234", ret.getId());
        Assert.assertEquals(OrderStatus.APPROVED, ret.getStatus());

    }

    @Test
    public void testComputerDeliverOrderSuccess() {
        //setup
        given(repository.save(any(Order.class))).willAnswer(defaultOrderAnswer("1234",OrderStatus.DELIVERED));
        given(repository.findById("1234")).willReturn(Optional.of(
                newOrder("1234",OrderStatus.APPROVED)
        ));

        //exec
        Order ret = service.deliverOrder("1234");

        //assert
        ArgumentCaptor<Order> argument = ArgumentCaptor.forClass(Order.class);

        verify(repository, atLeastOnce()).save(argument.capture());


        Assert.assertEquals("1234", ret.getId());
        Assert.assertEquals(OrderStatus.DELIVERED, ret.getStatus());

    }



    @Test
    public void testComputerCancelStatusDeliveredWithException() {
        //setup
        given(repository.findById("1234")).willReturn(Optional.of(newOrder("1234",OrderStatus.DELIVERED)));
        exceptionExpect.expectMessage("Cannot be canceled");
        exceptionExpect.expect(RuntimeException.class);

        //exec
        Order ret = service.cancelOrder("1234");

        //assert

    }


    @Test
    public void testComputerApproveStatusDeliveredWithException() {
        //setup
        given(repository.findById("1234")).willReturn(Optional.of(newOrder("1234",OrderStatus.DELIVERED)));
        exceptionExpect.expectMessage("Cannot be approved");
        exceptionExpect.expect(RuntimeException.class);

        //exec
        Order ret = service.approveOrder("1234");

        //assert

    }


    @Test
    public void testComputerDeliverStatusDeliveredWithException() {
        //setup
        given(repository.findById("1234")).willReturn(Optional.of(newOrder("1234",OrderStatus.DELIVERED)));
        exceptionExpect.expectMessage("Cannot be delivered");
        exceptionExpect.expect(RuntimeException.class);

        //exec
        Order ret = service.deliverOrder("1234");

        //assert

    }
}
