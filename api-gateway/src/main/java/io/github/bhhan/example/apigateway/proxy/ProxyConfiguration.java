package io.github.bhhan.example.apigateway.proxy;

import io.github.bhhan.example.apigateway.common.CommonConfiguration;
import io.github.bhhan.example.apigateway.customer.CustomerConfiguration;
import io.github.bhhan.example.apigateway.customer.CustomerDestinations;
import io.github.bhhan.example.apigateway.order.OrderConfiguration;
import io.github.bhhan.example.apigateway.order.OrderDestinations;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import io.github.resilience4j.timelimiter.TimeLimiterRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;


/**
 * Created by hbh5274@gmail.com on 2021-01-14
 * Github : http://github.com/bhhan5274
 */

@Configuration
@Import({CommonConfiguration.class, OrderConfiguration.class, CustomerConfiguration.class})
public class ProxyConfiguration {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${apigateway.timeout.millis}")
    private long apiGatewayTimeoutMillis;

    @Bean
    public OrderServiceProxy orderServiceProxy(OrderDestinations orderDestinations, WebClient webClient, CircuitBreakerRegistry circuitBreakerRegistry, TimeLimiterRegistry timeLimiterRegistry){
        return new OrderServiceProxy(orderDestinations, webClient, circuitBreakerRegistry, timeLimiterRegistry);
    }

    @Bean
    public CustomerServiceProxy customerServiceProxy(CustomerDestinations customerDestinations,WebClient webClient, CircuitBreakerRegistry circuitBreakerRegistry, TimeLimiterRegistry timeLimiterRegistry){
        return new CustomerServiceProxy(webClient, circuitBreakerRegistry, customerDestinations.getCustomerServiceUrl(), timeLimiterRegistry);
    }

    @Bean
    public TimeLimiterRegistry timeLimiterRegistry(){
        logger.info("apiGatewayTimeoutMillis={}", apiGatewayTimeoutMillis);
        return TimeLimiterRegistry.of(TimeLimiterConfig.custom()
            .timeoutDuration(Duration.ofMillis(apiGatewayTimeoutMillis)).build());
    }

    @Bean
    public Customizer<ReactiveResilience4JCircuitBreakerFactory> defaultCustomizer(){
        return factory -> factory.configureDefault(id -> new Resilience4JConfigBuilder(id)
            .circuitBreakerConfig(CircuitBreakerConfig.ofDefaults())
            .timeLimiterConfig(TimeLimiterConfig.custom().timeoutDuration(Duration.ofMillis(apiGatewayTimeoutMillis)).build()).build());
    }
}
