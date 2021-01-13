package io.github.bhhan.example.customer;

import io.eventuate.tram.spring.consumer.kafka.EventuateTramKafkaMessageConsumerConfiguration;
import io.eventuate.tram.spring.messaging.producer.jdbc.TramMessageProducerJdbcConfiguration;
import io.github.bhhan.example.customer.web.CustomerWebConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Created by hbh5274@gmail.com on 2021-01-13
 * Github : http://github.com/bhhan5274
 */

@SpringBootApplication
@Configuration
@Import({CustomerConfiguration.class, CustomerWebConfiguration.class, TramMessageProducerJdbcConfiguration.class, EventuateTramKafkaMessageConsumerConfiguration.class})
public class CustomerServiceMain {
    public static void main(String[] args) {
        SpringApplication.run(CustomerServiceMain.class, args);
    }
}
