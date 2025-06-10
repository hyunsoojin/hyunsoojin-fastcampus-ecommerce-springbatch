package fastcampus.ecommerce.api.dto.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class PaymentRequest {

  private boolean success;

  public static PaymentRequest of(boolean success) {
    return new PaymentRequest(success);
  }
}
