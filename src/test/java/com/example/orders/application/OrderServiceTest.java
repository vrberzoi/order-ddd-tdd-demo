package com.example.orders.application;

import com.example.orders.domain.Order;
import com.example.orders.domain.OrderRepository;
import com.example.orders.domain.OrderStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {
    @Mock OrderRepository repository;
    @InjectMocks OrderService service;

    @Test
    void createsAndSavesOrder() {
        when(repository.save(any(Order.class))).thenAnswer(invocation -> invocation.getArgument(0));
        Order result = service.create("Mouse", 2, new BigDecimal("10.00"));
        assertEquals("Mouse", result.getProductName());
        verify(repository).save(any(Order.class));
    }

    @Test
    void paysExistingOrder() {
        Order order = Order.create("Mouse", 1, new BigDecimal("10.00"));
        when(repository.findById(order.getId())).thenReturn(Optional.of(order));
        when(repository.save(order)).thenReturn(order);

        Order result = service.pay(order.getId());

        assertEquals(OrderStatus.PAID, result.getStatus());
        verify(repository).save(order);
    }

    @Test
    void throwsWhenOrderDoesNotExist() {
        UUID id = UUID.randomUUID();
        when(repository.findById(id)).thenReturn(Optional.empty());
        assertThrows(OrderNotFoundException.class, () -> service.get(id));
    }
}
