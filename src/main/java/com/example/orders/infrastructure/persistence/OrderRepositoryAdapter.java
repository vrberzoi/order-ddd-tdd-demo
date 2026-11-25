package com.example.orders.infrastructure.persistence;

import com.example.orders.domain.Order;
import com.example.orders.domain.OrderRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class OrderRepositoryAdapter implements OrderRepository {
    private final SpringDataOrderRepository jpa;

    public OrderRepositoryAdapter(SpringDataOrderRepository jpa) {
        this.jpa = jpa;
    }

    @Override
    public Order save(Order order) {
        return toDomain(jpa.save(toEntity(order)));
    }

    @Override
    public Optional<Order> findById(UUID id) {
        return jpa.findById(id).map(this::toDomain);
    }

    @Override
    public List<Order> findAll() {
        return jpa.findAll().stream().map(this::toDomain).toList();
    }

    private OrderJpaEntity toEntity(Order order) {
        return new OrderJpaEntity(order.getId(), order.getProductName(), order.getQuantity(),
                order.getUnitPrice(), order.getStatus());
    }

    private Order toDomain(OrderJpaEntity entity) {
        return Order.reconstitute(entity.getId(), entity.getProductName(), entity.getQuantity(),
                entity.getUnitPrice(), entity.getStatus());
    }
}
