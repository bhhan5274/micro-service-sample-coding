package io.github.bhhan.example.order.saga.createorder;

import io.github.bhhan.example.common.domain.Money;
import io.github.bhhan.example.customer.api.commands.ReserveCreditCommand;
import io.github.bhhan.example.customer.api.replies.CustomerCreditLimitExceeded;
import io.github.bhhan.example.order.common.OrderDetails;
import io.github.bhhan.example.order.common.OrderState;
import io.github.bhhan.example.order.common.RejectionReason;
import io.github.bhhan.example.order.domain.Order;
import io.github.bhhan.example.order.domain.OrderRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.stubbing.Answer;

import java.util.Optional;

import static io.eventuate.tram.sagas.testing.SagaUnitTestSupport.given;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by hbh5274@gmail.com on 2021-01-13
 * Github : http://github.com/bhhan5274
 */
public class CreateOrderSagaTest {
    private OrderRepository orderRepository;
    private Long customerId = 102L;
    private Money orderTotal = new Money("12.34");
    private OrderDetails orderDetails = new OrderDetails(customerId, orderTotal);
    private Long orderId = 103L;
    private Order order;

    private CreateOrderSaga makeCreateOrderSaga(){
        return new CreateOrderSaga(orderRepository);
    }

    @Before
    public void setUp(){
        orderRepository = mock(OrderRepository.class);
    }

    @Test
    public void shouldCreateOrder() {
        when(orderRepository.save(any(Order.class))).then((Answer<Order>) invocation -> {
            order = invocation.getArgument(0);
            order.setId(orderId);
            return order;
        });

        when(orderRepository.findById(orderId)).then(invocation -> Optional.of(order));

        given()
                .saga(makeCreateOrderSaga(),
                        new CreateOrderSagaData(orderDetails)).
                expect().
                command(new ReserveCreditCommand(customerId, orderId, orderTotal))
                .to("customerService")
                .andGiven()
                .successReply()
                .expectCompletedSuccessfully();

        assertEquals(OrderState.APPROVED, order.getState());
    }

    @Test
    public void shouldRejectCreateOrder() {
        when(orderRepository.save(any(Order.class))).then((Answer<Order>) invocation -> {
            order = invocation.getArgument(0);
            order.setId(orderId);
            return order;
        });

        when(orderRepository.findById(orderId)).then(invocation -> Optional.of(order));

        CreateOrderSagaData data = new CreateOrderSagaData(orderDetails);

        given()
                .saga(makeCreateOrderSaga(),
                        data).
                expect().
                command(new ReserveCreditCommand(customerId, orderId, orderTotal))
                .to("customerService")
                .andGiven()
                .failureReply(new CustomerCreditLimitExceeded())
                .expectRolledBack()
                .assertSagaData(sd -> {
                    assertEquals(RejectionReason.INSUFFICIENT_CREDIT, sd.getRejectionReason());
                });

        assertEquals(OrderState.REJECTED, order.getState());
        assertEquals(RejectionReason.INSUFFICIENT_CREDIT, order.getRejectionReason());

    }
}
