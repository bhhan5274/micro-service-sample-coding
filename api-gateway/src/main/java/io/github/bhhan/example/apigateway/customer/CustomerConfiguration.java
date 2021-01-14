package io.github.bhhan.example.apigateway.customer;

import io.github.bhhan.example.apigateway.proxy.CustomerServiceProxy;
import io.github.bhhan.example.apigateway.proxy.OrderServiceProxy;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;

/**
 * Created by hbh5274@gmail.com on 2021-01-14
 * Github : http://github.com/bhhan5274
 */

@Configuration
@EnableConfigurationProperties(CustomerDestinations.class)
public class CustomerConfiguration {
    @Bean
    public RouterFunction<ServerResponse> orderHistoryHandlerRouting(OrderHistoryHandlers orderHistoryHandlers){
        return RouterFunctions.route(GET("/customers/{customerId}/orderhistory"), orderHistoryHandlers::getOrderHistory);
    }

    @Bean
    public OrderHistoryHandlers orderHistoryHandlers(OrderServiceProxy orderServiceProxy, CustomerServiceProxy customerServiceProxy){
        return new OrderHistoryHandlers(orderServiceProxy, customerServiceProxy);
    }

    @Bean
    public RouteLocator customerProxyRouting(RouteLocatorBuilder builder, CustomerDestinations customerDestinations){
        return builder.routes()
                .route(r -> r.path("/customers/**").and().method(HttpMethod.GET.name()).uri(customerDestinations.getCustomerServiceUrl()))
                .build();
    }
}
