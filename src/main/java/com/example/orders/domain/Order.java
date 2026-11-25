package com.example.orders.domain;

import java.math.BigDecimal;
import java.util.UUID;

public class Order {
    private UUID id;
    private String productName;
    private int quantity;
    private BigDecimal unitPrice;
    private OrderStatus status;

    private Order(UUID id, String productName, int quantity, BigDecimal unitPrice, OrderStatus status) {
        this.id = id;
        this.productName = productName;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.status = status;
    }

    public static Order create(String productName, int quantity, BigDecimal unitPrice) {
        if (productName == null || productName.isBlank()) throw new IllegalArgumentException("Product name is required");
        if (quantity <= 0) throw new IllegalArgumentException("Quantity must be greater than zero");
        if (unitPrice == null || unitPrice.signum() <= 0) throw new IllegalArgumentException("Price must be greater than zero");
        return new Order(UUID.randomUUID(), productName.trim(), quantity, unitPrice, OrderStatus.CREATED);
    }

    public void pay() {
        if (status != OrderStatus.CREATED) throw new IllegalStateException("Only CREATED orders can be paid");
        status = OrderStatus.PAID;
    }

    public void cancel() {
        if (status != OrderStatus.CREATED) throw new IllegalStateException("Only CREATED orders can be cancelled");
        status = OrderStatus.CANCELLED;
    }

    public UUID getId() { return id; }
    public String getProductName() { return productName; }
    public int getQuantity() { return quantity; }
    public BigDecimal getUnitPrice() { return unitPrice; }
    public BigDecimal getTotal() { return unitPrice.multiply(BigDecimal.valueOf(quantity)); }
    public OrderStatus getStatus() { return status; }

    // Used by persistence adapter when reconstructing a domain object
    public static Order reconstitute(UUID id, String productName, int quantity, BigDecimal unitPrice, OrderStatus status) {
        return new Order(id, productName, quantity, unitPrice, status);
    }
}
