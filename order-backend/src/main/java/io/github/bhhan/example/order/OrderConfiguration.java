package io.github.bhhan.example.order;

import io.eventuate.tram.sagas.orchestration.SagaInstanceFactory;
import io.eventuate.tram.sagas.spring.orchestration.SagaOrchestratorConfiguration;
import io.eventuate.tram.spring.optimisticlocking.OptimisticLockingDecoratorConfiguration;
import io.github.bhhan.example.order.domain.OrderRepository;
import io.github.bhhan.example.order.saga.createorder.CreateOrderSaga;
import io.github.bhhan.example.order.service.OrderService;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Created by hbh5274@gmail.com on 2021-01-13
 * Github : http://github.com/bhhan5274
 */

@Configuration
@EnableJpaRepositories
@EnableAutoConfiguration
@Import({SagaOrchestratorConfiguration.class, OptimisticLockingDecoratorConfiguration.class})
public class OrderConfiguration {
    @Bean
    public OrderService orderService(OrderRepository orderRepository, SagaInstanceFactory sagaInstanceFactory, CreateOrderSaga createOrderSaga){
        return new OrderService(orderRepository, sagaInstanceFactory, createOrderSaga);
    }

    @Bean
    public CreateOrderSaga createOrderSaga(OrderRepository orderRepository){
        return new CreateOrderSaga(orderRepository);
    }
}
