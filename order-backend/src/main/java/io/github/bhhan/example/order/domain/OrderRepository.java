package io.github.bhhan.example.order.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by hbh5274@gmail.com on 2021-01-13
 * Github : http://github.com/bhhan5274
 */
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByOrderDetailsCustomerId(Long customerId);
}
