package io.github.bhhan.example.apigateway.order;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

/**
 * Created by hbh5274@gmail.com on 2021-01-14
 * Github : http://github.com/bhhan5274
 */

@Configuration
@EnableConfigurationProperties(OrderDestinations.class)
public class OrderConfiguration {
    @Bean
    public RouteLocator orderProxyRouting(RouteLocatorBuilder builder, OrderDestinations orderDestinations){
        return builder.routes()
                .route(r -> r.path("/orders/customer/**").and().method(HttpMethod.GET.name()).uri(orderDestinations.getOrderServiceUrl()))
                .build();
    }
}
