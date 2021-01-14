package io.github.bhhan.example.apigateway.proxy;

import io.github.bhhan.example.customer.webapi.GetCustomerResponse;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.reactor.circuitbreaker.operator.CircuitBreakerOperator;
import io.github.resilience4j.reactor.timelimiter.TimeLimiterOperator;
import io.github.resilience4j.timelimiter.TimeLimiter;
import io.github.resilience4j.timelimiter.TimeLimiterRegistry;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Optional;

/**
 * Created by hbh5274@gmail.com on 2021-01-14
 * Github : http://github.com/bhhan5274
 */
public class CustomerServiceProxy {
    private CircuitBreaker cb;
    private WebClient client;
    private String customerServiceUrl;
    private TimeLimiter timeLimiter;

    public CustomerServiceProxy(WebClient client, CircuitBreakerRegistry circuitBreakerRegistry, String customerServiceUrl, TimeLimiterRegistry timeLimiterRegistry) {
        this.client = client;
        this.customerServiceUrl = customerServiceUrl;
        this.timeLimiter = timeLimiterRegistry.timeLimiter("MY_TIME_LIMITER");
        this.cb = circuitBreakerRegistry.circuitBreaker("MY_CIRCUIT_BREAKER");
    }

    public Mono<Optional<GetCustomerResponse>> findCustomerById(String customerId){
        Mono<ClientResponse> response = client.get()
                .uri(customerServiceUrl + "/customers/{customerId}", customerId)
                .exchange();

        return response.flatMap(resp -> {
            switch (resp.statusCode()){
                case OK:
                    return resp.bodyToMono(GetCustomerResponse.class).map(Optional::of);
                case NOT_FOUND:
                    Mono<Optional<GetCustomerResponse>> notFound = Mono.just(Optional.empty());
                    return notFound;
                default:
                    return Mono.error(new UnknownProxyException("Unknown: " + resp.statusCode()));
            }
        })
        .transformDeferred(TimeLimiterOperator.of(timeLimiter))
        .transformDeferred(CircuitBreakerOperator.of(cb));
    }
}
