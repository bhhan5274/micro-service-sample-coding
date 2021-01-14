package io.github.bhhan.example.apigateway.customer;

import io.github.bhhan.example.apigateway.proxy.CustomerServiceProxy;
import io.github.bhhan.example.apigateway.proxy.OrderServiceProxy;
import io.github.bhhan.example.apigateway.webapi.GetCustomerHistoryResponse;
import io.github.bhhan.example.customer.webapi.GetCustomerResponse;
import io.github.bhhan.example.order.webapi.GetOrderResponse;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

import static org.springframework.web.reactive.function.BodyInserters.fromValue;

/**
 * Created by hbh5274@gmail.com on 2021-01-14
 * Github : http://github.com/bhhan5274
 */
public class OrderHistoryHandlers {
    private OrderServiceProxy orderServiceProxy;
    private CustomerServiceProxy customerServiceProxy;

    public OrderHistoryHandlers(OrderServiceProxy orderServiceProxy, CustomerServiceProxy customerServiceProxy) {
        this.orderServiceProxy = orderServiceProxy;
        this.customerServiceProxy = customerServiceProxy;
    }

    public Mono<ServerResponse> getOrderHistory(ServerRequest serverRequest){
        String customerId = serverRequest.pathVariable("customerId");

        Mono<Optional<GetCustomerResponse>> customer = customerServiceProxy.findCustomerById(customerId);
        Mono<List<GetOrderResponse>> orders = orderServiceProxy.findOrderByCustomerId(customerId);

        Mono<Optional<GetCustomerHistoryResponse>> map = Mono.zip(customer, orders)
                .map(passibleCustomerAndOrders -> passibleCustomerAndOrders.getT1()
                        .map(c -> {
                            List<GetOrderResponse> os = passibleCustomerAndOrders.getT2();
                            return new GetCustomerHistoryResponse(c.getCustomerId(), c.getName(), c.getCreditLimit(), os);
                        }));
        return map.flatMap(maybe -> maybe.map(c -> ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(fromValue(c)))
                .orElseGet(() -> ServerResponse.notFound().build()));
    }
}
