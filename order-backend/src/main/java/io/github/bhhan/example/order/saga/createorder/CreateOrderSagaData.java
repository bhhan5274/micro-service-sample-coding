package io.github.bhhan.example.order.saga.createorder;

import io.github.bhhan.example.order.common.OrderDetails;
import io.github.bhhan.example.order.common.RejectionReason;

/**
 * Created by hbh5274@gmail.com on 2021-01-13
 * Github : http://github.com/bhhan5274
 */

public class CreateOrderSagaData  {

    private OrderDetails orderDetails;
    private Long orderId;
    private RejectionReason rejectionReason;

    public CreateOrderSagaData(OrderDetails orderDetails) {
        this.orderDetails = orderDetails;
    }

    public CreateOrderSagaData() {
    }

    public Long getOrderId() {
        return orderId;
    }

    public OrderDetails getOrderDetails() {
        return orderDetails;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public void setRejectionReason(RejectionReason rejectionReason) {
        this.rejectionReason = rejectionReason;
    }

    public RejectionReason getRejectionReason() {
        return rejectionReason;
    }
}
