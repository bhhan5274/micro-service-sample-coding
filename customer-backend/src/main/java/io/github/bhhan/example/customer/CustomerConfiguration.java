package io.github.bhhan.example.customer;

import io.eventuate.tram.commands.consumer.CommandDispatcher;
import io.eventuate.tram.sagas.participant.SagaCommandDispatcherFactory;
import io.eventuate.tram.sagas.spring.participant.SagaParticipantConfiguration;
import io.eventuate.tram.spring.optimisticlocking.OptimisticLockingDecoratorConfiguration;
import io.github.bhhan.example.customer.domain.CustomerRepository;
import io.github.bhhan.example.customer.service.CustomerCommandHandler;
import io.github.bhhan.example.customer.service.CustomerService;
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
@Import({SagaParticipantConfiguration.class, OptimisticLockingDecoratorConfiguration.class})
@EnableJpaRepositories
@EnableAutoConfiguration
public class CustomerConfiguration {
    @Bean
    public CustomerService customerService(CustomerRepository customerRepository){
        return new CustomerService(customerRepository);
    }

    @Bean
    public CustomerCommandHandler customerCommandHandler(CustomerService customerService){
        return new CustomerCommandHandler(customerService);
    }

    @Bean
    public CommandDispatcher customerCommandDispatcher(CustomerCommandHandler target,
                                                       SagaCommandDispatcherFactory sagaCommandDispatcherFactory){
        return sagaCommandDispatcherFactory.make("customerCommandDispatcher", target.commandHandlerDefinitions());
    }
}
