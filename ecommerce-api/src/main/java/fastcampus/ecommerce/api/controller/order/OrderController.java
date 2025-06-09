package fastcampus.ecommerce.api.controller.order;

import fastcampus.ecommerce.api.dto.order.OrderRequest;
import fastcampus.ecommerce.api.dto.order.OrderResponse;
import fastcampus.ecommerce.api.service.order.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/orders")
@RequiredArgsConstructor
public class OrderController {

  @PostMapping("")
  public OrderResponse createOrder(@RequestBody OrderRequest orderRequest) {
    return OrderResponse.from(orderService.order(orderRequest.getCustomerId(),
        orderRequest.getOrderItemsCommand(), orderRequest.getPaymentMethod()));
  }

  private final OrderService orderService;

}
