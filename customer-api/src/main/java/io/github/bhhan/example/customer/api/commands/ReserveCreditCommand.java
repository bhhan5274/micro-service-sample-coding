package io.github.bhhan.example.customer.api.commands;

import io.eventuate.tram.commands.common.Command;
import io.github.bhhan.example.common.domain.Money;

/**
 * Created by hbh5274@gmail.com on 2021-01-13
 * Github : http://github.com/bhhan5274
 */

public class ReserveCreditCommand implements Command {
    private Long orderId;
    private Money orderTotal;
    private long customerId;

    public ReserveCreditCommand() {
    }

    public ReserveCreditCommand(Long customerId, Long orderId, Money orderTotal) {
        this.customerId = customerId;
        this.orderId = orderId;
        this.orderTotal = orderTotal;
    }

    public Money getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(Money orderTotal) {
        this.orderTotal = orderTotal;
    }

    public Long getOrderId() {

        return orderId;
    }

    public void setOrderId(Long orderId) {

        this.orderId = orderId;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }
}
