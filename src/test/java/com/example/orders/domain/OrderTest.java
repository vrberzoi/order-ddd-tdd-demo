package com.example.orders.domain;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

class OrderTest {
    @Test
    void newOrderStartsAsCreatedAndCalculatesTotal() {
        Order order = Order.create("Keyboard", 2, new BigDecimal("25.50"));
        assertEquals(OrderStatus.CREATED, order.getStatus());
        assertEquals(new BigDecimal("51.00"), order.getTotal());
    }

    @Test
    void createdOrderCanBePaid() {
        Order order = Order.create("Keyboard", 1, new BigDecimal("25.50"));
        order.pay();
        assertEquals(OrderStatus.PAID, order.getStatus());
    }

    @Test
    void cancelledOrderCannotBePaid() {
        Order order = Order.create("Keyboard", 1, new BigDecimal("25.50"));
        order.cancel();
        assertThrows(IllegalStateException.class, order::pay);
    }

    @Test
    void paidOrderCannotBeCancelled() {
        Order order = Order.create("Keyboard", 1, new BigDecimal("25.50"));
        order.pay();
        assertThrows(IllegalStateException.class, order::cancel);
    }

    @Test
    void quantityMustBePositive() {
        assertThrows(IllegalArgumentException.class,
                () -> Order.create("Keyboard", 0, new BigDecimal("25.50")));
    }
}
