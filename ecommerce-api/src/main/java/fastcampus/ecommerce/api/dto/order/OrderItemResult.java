package fastcampus.ecommerce.api.dto.order;

import fastcampus.ecommerce.api.domain.order.OrderItem;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderItemResult {

  private Long orderItemId;

  private Integer quantity;
  private Integer unitPrice;
  private String productId;

  public static OrderItemResult from(OrderItem orderItem) {
    return new OrderItemResult(orderItem.getOrderItemId(), orderItem.getQuantity(),
        orderItem.getUnitPrice(), orderItem.getProductId());
  }
}
