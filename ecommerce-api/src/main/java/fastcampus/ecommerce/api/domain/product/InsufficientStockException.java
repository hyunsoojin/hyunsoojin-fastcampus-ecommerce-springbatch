package fastcampus.ecommerce.api.domain.product;

public class InsufficientStockException extends RuntimeException {

  public InsufficientStockException() {
    super("재고가 부족합니다.");
  }
}
