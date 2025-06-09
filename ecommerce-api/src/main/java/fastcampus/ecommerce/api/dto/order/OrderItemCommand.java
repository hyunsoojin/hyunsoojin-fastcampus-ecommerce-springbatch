package fastcampus.ecommerce.api.dto.order;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderItemCommand {

  private Integer quantity;
  private String productId;

  public static OrderItemCommand of(Integer quantity, String productId) {
    return new OrderItemCommand(quantity, productId);
  }
}
