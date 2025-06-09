package fastcampus.ecommerce.api.domain.product;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

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
    assertThatThrownBy(() -> product.increaseStock(Integer.MAX_VALUE))
        .isInstanceOf(StockQuantityArithmeticException.class);
  }

  @ParameterizedTest
  @ValueSource(ints = {-10, -1, 0})
  void testIncreaseStockPositiveParameter(int notPositiveQuantity) {
    assertThatThrownBy(() -> product.increaseStock(notPositiveQuantity))
        .isInstanceOf(InvalidStockQuantityException.class);
  }

  @Test
  void testDecreaseStock() {
    product.decreaseStock(50);
    assertThat(product.getStockQuantity()).isEqualTo(50);
  }

  @ParameterizedTest
  @ValueSource(ints = {-10, -1, 0})
  void testDecreaseStockPositiveParameter(int notPositiveQuantity) {
    assertThatThrownBy(() -> product.decreaseStock(notPositiveQuantity))
        .isInstanceOf(InvalidStockQuantityException.class);
  }

  @Test
  void testDecreaseStockInsufficientStock() {
    assertThatThrownBy(() -> product.decreaseStock(101))
        .isInstanceOf(InsufficientStockException.class);
  }
}