package io.github.bhhan.example.order.webapi;

import io.github.bhhan.example.order.common.OrderState;
import io.github.bhhan.example.order.common.RejectionReason;

/**
 * Created by hbh5274@gmail.com on 2021-01-13
 * Github : http://github.com/bhhan5274
 */

public class GetOrderResponse {
    private Long orderId;
    private OrderState orderState;
    private RejectionReason rejectionReason;

    public GetOrderResponse() {
    }

    public GetOrderResponse(Long orderId, OrderState orderState, RejectionReason rejectionReason) {
        this.orderId = orderId;
        this.orderState = orderState;
        this.rejectionReason = rejectionReason;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public OrderState getOrderState() {
        return orderState;
    }

    public void setOrderState(OrderState orderState) {
        this.orderState = orderState;
    }

    public RejectionReason getRejectionReason() {
        return rejectionReason;
    }

    public void setRejectionReason(RejectionReason rejectionReason) {
        this.rejectionReason = rejectionReason;
    }
}
