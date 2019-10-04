package com.seumanuel.store.helper;

import com.seumanuel.store.model.Order;
import com.seumanuel.store.model.OrderStatus;
import org.mockito.stubbing.Answer;

import java.math.BigDecimal;


public final class OrderHelper {

    private OrderHelper() {
    }


    public static Order newOrder(String id) {
        Order ret = new Order();
        ret.setId(id);
        ret.setStatus(OrderStatus.NEW);
        return ret;
    }

    public static Order newOrder(String id, OrderStatus os) {
        Order ret = new Order();
        ret.setId(id);
        ret.setStatus(os);
        return ret;
    }

    public static Answer<Order> defaultOrderAnswer(final String id) {
        return (Answer<Order>) invocationOnMock -> {
            Order arg = invocationOnMock.getArgument(0);

            Order ret = newOrder(arg.getId()
            );

            return ret;
        };
    }

    public static Answer<Order> defaultOrderAnswer(final String id, final OrderStatus os) {
        return (Answer<Order>) invocationOnMock -> {
            Order arg = invocationOnMock.getArgument(0);

            Order ret = newOrder(arg.getId()
            );
            ret.setStatus(os);

            return ret;
        };
    }

}
