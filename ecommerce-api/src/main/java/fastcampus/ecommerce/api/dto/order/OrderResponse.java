package fastcampus.ecommerce.api.dto.order;

import fastcampus.ecommerce.api.domain.order.OrderStatus;
import fastcampus.ecommerce.api.domain.payment.PaymentMethod;
import fastcampus.ecommerce.api.domain.payment.PaymentStatus;
import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderResponse {

  private Long orderId;
  private Timestamp orderDate;
  private Long customerId;
  private OrderStatus orderStatus;

  private List<OrderItemResponse> orderItems;
  private Long productCount;
  private Long totalItemQuantity;

  private Long paymentId;
  private PaymentMethod paymentMethod;
  private PaymentStatus paymentStatus;
  private Timestamp paymentDate;
  private Integer totalAmount;
  private boolean paymentSuccess;

  public static OrderResponse from(OrderResult orderResult) {
    return new OrderResponse(
        orderResult.getOrderId(),
        orderResult.getOrderDate(),
        orderResult.getCustomerId(),
        orderResult.getOrderStatus(),
        orderResult.getOrderItems().stream()
            .map(OrderItemResponse::from)
            .collect(Collectors.toList()),
        orderResult.getProductCount(),
        orderResult.getTotalItemQuantity(),
        orderResult.getPaymentId(),
        orderResult.getPaymentMethod(),
        orderResult.getPaymentStatus(),
        orderResult.getPaymentDate(),
        orderResult.getTotalAmount(),
        orderResult.isPaymentSuccess()
    );
  }
}
