package io.github.bhhan.example.order.domain;

import io.github.bhhan.example.order.common.OrderDetails;
import io.github.bhhan.example.order.common.OrderState;
import io.github.bhhan.example.order.common.RejectionReason;

import javax.persistence.*;

/**
 * Created by hbh5274@gmail.com on 2021-01-13
 * Github : http://github.com/bhhan5274
 */

@Entity
@Table(name="orders")
@Access(AccessType.FIELD)
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private OrderState state;

    @Embedded
    private OrderDetails orderDetails;

    @Enumerated(EnumType.STRING)
    private RejectionReason rejectionReason;

    @Version
    private Long version;

    public Order() {
    }

    public Order(OrderDetails orderDetails) {
        this.orderDetails = orderDetails;
        this.state = OrderState.PENDING;
    }

    public static Order createOrder(OrderDetails orderDetails) {
        return new Order(orderDetails);
    }

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void approve() {
        this.state = OrderState.APPROVED;
    }

    public void reject(RejectionReason rejectionReason) {
        this.state = OrderState.REJECTED;
        this.rejectionReason = rejectionReason;
    }

    public OrderState getState() {
        return state;
    }

    public RejectionReason getRejectionReason() {
        return rejectionReason;
    }
}
