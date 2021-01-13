package io.github.bhhan.example.order.web;

import io.github.bhhan.example.order.common.OrderDetails;
import io.github.bhhan.example.order.domain.Order;
import io.github.bhhan.example.order.domain.OrderRepository;
import io.github.bhhan.example.order.service.OrderService;
import io.github.bhhan.example.order.webapi.CreateOrderRequest;
import io.github.bhhan.example.order.webapi.CreateOrderResponse;
import io.github.bhhan.example.order.webapi.GetOrderResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by hbh5274@gmail.com on 2021-01-13
 * Github : http://github.com/bhhan5274
 */

@RestController
public class OrderController {

    private OrderService orderService;
    private OrderRepository orderRepository;

    @Autowired
    public OrderController(OrderService orderService, OrderRepository orderRepository) {
        this.orderService = orderService;
        this.orderRepository = orderRepository;
    }

    @RequestMapping(value = "/orders", method = RequestMethod.POST)
    public CreateOrderResponse createOrder(@RequestBody CreateOrderRequest createOrderRequest) {
        Order order = orderService.createOrder(new OrderDetails(createOrderRequest.getCustomerId(), createOrderRequest.getOrderTotal()));
        return new CreateOrderResponse(order.getId());
    }

    @RequestMapping(value="/orders/{orderId}", method= RequestMethod.GET)
    public ResponseEntity<GetOrderResponse> getOrder(@PathVariable Long orderId) {
        return orderRepository
                .findById(orderId)
                .map(o -> new ResponseEntity<>(new GetOrderResponse(o.getId(), o.getState(), o.getRejectionReason()), HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @RequestMapping(value="/orders/customer/{customerId}", method= RequestMethod.GET)
    public ResponseEntity<List<GetOrderResponse>> getOrdersByCustomerId(@PathVariable Long customerId) {
        return new ResponseEntity<>(orderRepository
                .findAllByOrderDetailsCustomerId(customerId)
                .stream()
                .map(o -> new GetOrderResponse(o.getId(), o.getState(), o.getRejectionReason()))
                .collect(Collectors.toList()), HttpStatus.OK);
    }
}

