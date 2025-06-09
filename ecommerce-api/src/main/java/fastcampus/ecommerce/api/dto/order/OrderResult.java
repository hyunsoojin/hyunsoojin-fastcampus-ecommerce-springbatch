package fastcampus.ecommerce.api.dto.order;

import fastcampus.ecommerce.api.domain.order.Order;
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
public class OrderResult {

  private Long orderId;
  private Timestamp orderDate;
  private Long customerId;
  private OrderStatus orderStatus;

  private List<OrderItemResult> orderItems;
  private Long productCount;
  private Long totalItemQuantity;

  private Long paymentId;
  private PaymentMethod paymentMethod;
  private PaymentStatus paymentStatus;
  private Timestamp paymentDate;
  private Integer totalAmount;
  private boolean paymentSuccess;

  public static OrderResult from(Order order) {
    return new OrderResult(
        order.getOrderId(),
        order.getOrderDate(),
        order.getCustomerId(),
        order.getOrderStatus(),
        order.getOrderItems().stream()
            .map(OrderItemResult::from)
            .collect(Collectors.toList()),
        order.countProducts(),
        order.calculateTotalItemQuantity(),
        order.getPaymentId(),
        order.getPaymentMethod(),
        order.getPaymentStatus(),
        order.getPaymentDate(),
        order.calculateTotalAmount(),
        order.isPaymentSuccess()
    );
  }
}
