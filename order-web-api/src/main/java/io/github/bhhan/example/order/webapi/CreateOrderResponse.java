package io.github.bhhan.example.order.webapi;

/**
 * Created by hbh5274@gmail.com on 2021-01-13
 * Github : http://github.com/bhhan5274
 */

public class CreateOrderResponse {
    private long orderId;

    public CreateOrderResponse() {
    }

    public CreateOrderResponse(long orderId) {
        this.orderId = orderId;
    }

    public long getOrderId() {
        return orderId;
    }
}
