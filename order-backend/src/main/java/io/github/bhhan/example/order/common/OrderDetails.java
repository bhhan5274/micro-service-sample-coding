package io.github.bhhan.example.order.common;

import io.github.bhhan.example.common.domain.Money;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;

/**
 * Created by hbh5274@gmail.com on 2021-01-13
 * Github : http://github.com/bhhan5274
 */

@Embeddable
public class OrderDetails {

    private Long customerId;

    @Embedded
    private Money orderTotal;

    public OrderDetails() {
    }

    public OrderDetails(Long customerId, Money orderTotal) {
        this.customerId = customerId;
        this.orderTotal = orderTotal;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public Money getOrderTotal() {
        return orderTotal;
    }
}