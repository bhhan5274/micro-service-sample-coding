package io.github.bhhan.example.apigateway.proxy;

import io.github.bhhan.example.apigateway.order.OrderDestinations;
import io.github.bhhan.example.order.webapi.GetOrderResponse;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.reactor.circuitbreaker.operator.CircuitBreakerOperator;
import io.github.resilience4j.reactor.timelimiter.TimeLimiterOperator;
import io.github.resilience4j.timelimiter.TimeLimiter;
import io.github.resilience4j.timelimiter.TimeLimiterRegistry;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

/**
 * Created by hbh5274@gmail.com on 2021-01-14
 * Github : http://github.com/bhhan5274
 */
public class OrderServiceProxy {
    private CircuitBreaker cb;
    private TimeLimiter timeLimiter;
    private OrderDestinations orderDestinations;
    private WebClient webClient;

    public OrderServiceProxy(OrderDestinations orderDestinations, WebClient webClient, CircuitBreakerRegistry circuitBreakerRegistry, TimeLimiterRegistry timeLimiterRegistry) {
        this.orderDestinations = orderDestinations;
        this.webClient = webClient;
        this.cb = circuitBreakerRegistry.circuitBreaker("MY_CIRCUIT_BREAKER");
        this.timeLimiter = timeLimiterRegistry.timeLimiter("MY_TIME_LIMITER");
    }

    public Mono<List<GetOrderResponse>> findOrderByCustomerId(String customerId){
        Mono<ClientResponse> response = webClient.get()
                .uri(orderDestinations.getOrderServiceUrl() + "/orders/customer/{customerId}", customerId)
                .exchange();

        return response.flatMap(resp -> {
            switch (resp.statusCode()){
                case OK:
                    return resp.bodyToMono(GetOrderResponse[].class).map(Arrays::asList);
                default:
                    return Mono.error(new UnknownProxyException("Unknown: " + resp.statusCode()));

            }
        })
        .transformDeferred(TimeLimiterOperator.of(timeLimiter))
        .transformDeferred(CircuitBreakerOperator.of(cb));
    }
}
