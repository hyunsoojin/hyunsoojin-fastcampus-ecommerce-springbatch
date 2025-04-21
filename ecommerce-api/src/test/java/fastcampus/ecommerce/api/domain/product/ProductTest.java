package fastcampus.ecommerce.api.domain.product;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProductTest {

  private Product product;

  @BeforeEach
  void setUp() {
    LocalDateTime now = LocalDateTime.now();
    product = Product.of("PRODUCT-1", 1L, "ELectronics", "TestProduct", LocalDate.now(),
        LocalDate.now(),
        ProductStatus.AVAILABLE, "Test Brand", "Test Manufacturer", 1000, 100, now, now);
  }

  @Test
  void testIncreaseStock() {
    product.increaseStock(50);
    assertThat(product.getStockQuantity()).isEqualTo(150);
  }

  @Test
  void testIncreaseSTockNegativeResult() {

    product.increaseStock(Integer.MAX_VALUE);
  }
}