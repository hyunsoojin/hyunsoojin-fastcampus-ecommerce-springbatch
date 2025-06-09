package fastcampus.ecommerce.api.domain.order;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import fastcampus.ecommerce.api.domain.payment.PaymentMethod;
import fastcampus.ecommerce.api.domain.payment.PaymentStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OrderTest {

  private Order order;

  @BeforeEach
  void setUp() {
    order = Order.createOrder(1L);
    order.addOrderItem("PRO0D01", 2, 100);
    order.initPayment(PaymentMethod.CREDIT_CARD);
  }

  @Test
  void testCompletePayment() {
    order.completePayment(true);
    assertAll(
        () -> assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.PROCESSING),
        () -> assertThat(order.getPaymentStatus()).isEqualTo(PaymentStatus.COMPLETED),
        () -> assertThat(order.isPaymentSuccess()).isTrue()
    );

  }

  @Test
  void testCompletePaymentFail() {
    order.completePayment(false);
    assertAll(
        () -> assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.PROCESSING),
        () -> assertThat(order.getPaymentStatus()).isEqualTo(PaymentStatus.FAILED),
        () -> assertThat(order.isPaymentSuccess()).isFalse()
    );
  }

  @Test
  void testCompletePaymentException() {
    order.completePayment(false);
    assertThatThrownBy(() -> order.completePayment(true))
        .isInstanceOf(IllegalOrderStateException.class);
  }

  @Test
  void testComplete() {
    order.completePayment(true);
    order.complete();
    assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.COMPLETED);
  }

  @Test
  void testCompleteOrderPaymentFail() {
    order.completePayment(false);

    assertThatThrownBy(() -> order.complete())
        .isInstanceOf(IllegalOrderStateException.class);
  }

  @Test
  void testCompleteOrderException() {
    assertThatThrownBy(() -> order.complete())
        .isInstanceOf(IllegalOrderStateException.class);
  }

  @Test
  void testOrderCancel() {
    order.completePayment(true);
    order.cancel();
    assertAll(
        () -> assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.CANCELLED),
        () -> assertThat(order.getPaymentStatus()).isEqualTo(PaymentStatus.REFUNDED)
    );
  }

  @Test
  void testOrderCancelAfterComplete() {
    order.completePayment(true);
    order.complete();
    assertThatThrownBy(() -> order.cancel())
        .isInstanceOf(IllegalOrderStateException.class);
  }
}