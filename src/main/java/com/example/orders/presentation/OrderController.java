package com.example.orders.presentation;

import com.example.orders.application.OrderService;
import com.example.orders.domain.Order;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @PostMapping
    public OrderResponse create(@RequestBody CreateOrderRequest request) {
        return OrderResponse.from(service.create(request.productName(), request.quantity(), request.unitPrice()));
    }

    @GetMapping("/{id}")
    public OrderResponse get(@PathVariable UUID id) {
        return OrderResponse.from(service.get(id));
    }

    @GetMapping
    public List<OrderResponse> getAll() {
        return service.getAll().stream().map(OrderResponse::from).toList();
    }

    @PostMapping("/{id}/pay")
    public OrderResponse pay(@PathVariable UUID id) {
        return OrderResponse.from(service.pay(id));
    }

    @PostMapping("/{id}/cancel")
    public OrderResponse cancel(@PathVariable UUID id) {
        return OrderResponse.from(service.cancel(id));
    }

    public record CreateOrderRequest(String productName, int quantity, BigDecimal unitPrice) {}
    public record OrderResponse(UUID id, String productName, int quantity, BigDecimal unitPrice,
                                BigDecimal total, String status) {
        static OrderResponse from(Order order) {
            return new OrderResponse(order.getId(), order.getProductName(), order.getQuantity(),
                    order.getUnitPrice(), order.getTotal(), order.getStatus().name());
        }
    }
}
