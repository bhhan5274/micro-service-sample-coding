package io.github.bhhan.example.order.service;

import io.eventuate.tram.sagas.orchestration.SagaInstanceFactory;
import io.github.bhhan.example.order.common.OrderDetails;
import io.github.bhhan.example.order.domain.Order;
import io.github.bhhan.example.order.domain.OrderRepository;
import io.github.bhhan.example.order.saga.createorder.CreateOrderSaga;
import io.github.bhhan.example.order.saga.createorder.CreateOrderSagaData;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by hbh5274@gmail.com on 2021-01-13
 * Github : http://github.com/bhhan5274
 */
public class OrderService {
    private OrderRepository orderRepository;
    private SagaInstanceFactory sagaInstanceFactory;
    private CreateOrderSaga createOrderSaga;

    public OrderService(OrderRepository orderRepository, SagaInstanceFactory sagaInstanceFactory, CreateOrderSaga createOrderSaga) {
        this.orderRepository = orderRepository;
        this.sagaInstanceFactory = sagaInstanceFactory;
        this.createOrderSaga = createOrderSaga;
    }

    @Transactional
    public Order createOrder(OrderDetails orderDetails) {
        CreateOrderSagaData data = new CreateOrderSagaData(orderDetails);
        sagaInstanceFactory.create(createOrderSaga, data);
        return orderRepository.findById(data.getOrderId()).get();
    }

}
