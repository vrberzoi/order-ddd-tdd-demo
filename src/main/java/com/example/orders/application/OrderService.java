package com.example.orders.application;

import com.example.orders.domain.Order;
import com.example.orders.domain.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class OrderService {
    private final OrderRepository repository;

    public OrderService(OrderRepository repository) {
        this.repository = repository;
    }

    public Order create(String productName, int quantity, BigDecimal unitPrice) {
        return repository.save(Order.create(productName, quantity, unitPrice));
    }

    @Transactional(readOnly = true)
    public Order get(UUID id) {
        return repository.findById(id).orElseThrow(() -> new OrderNotFoundException(id));
    }

    @Transactional(readOnly = true)
    public List<Order> getAll() {
        return repository.findAll();
    }

    public Order pay(UUID id) {
        Order order = get(id);
        order.pay();
        return repository.save(order);
    }

    public Order cancel(UUID id) {
        Order order = get(id);
        order.cancel();
        return repository.save(order);
    }
}
