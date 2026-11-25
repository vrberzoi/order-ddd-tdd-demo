package com.example.orders.infrastructure.persistence;

import com.example.orders.domain.OrderStatus;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "orders")
public class OrderJpaEntity {
    @Id
    private UUID id;
    private String productName;
    private int quantity;
    @Column(precision = 12, scale = 2)
    private BigDecimal unitPrice;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    protected OrderJpaEntity() {}

    public OrderJpaEntity(UUID id, String productName, int quantity, BigDecimal unitPrice, OrderStatus status) {
        this.id = id;
        this.productName = productName;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.status = status;
    }

    public UUID getId() { return id; }
    public String getProductName() { return productName; }
    public int getQuantity() { return quantity; }
    public BigDecimal getUnitPrice() { return unitPrice; }
    public OrderStatus getStatus() { return status; }
}
