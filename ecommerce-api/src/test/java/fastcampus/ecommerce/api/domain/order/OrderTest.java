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
  void testCompleteOrderPayment() {
    order.completePayment(true);
    assertAll(
        () -> assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.PROCESSING),
        () -> assertThat(order.getPaymentStatus()).isEqualTo(PaymentStatus.COMPLETED),
        () -> assertThat(order.isPaymentSuccess()).isTrue()
    );

  }

  @Test
  void testCompleteOrderPaymentFail() {
    order.completePayment(false);
    assertAll(
        () -> assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.PROCESSING),
        () -> assertThat(order.getPaymentStatus()).isEqualTo(PaymentStatus.FAILED),
        () -> assertThat(order.isPaymentSuccess()).isFalse()
    );
  }

  @Test
  void testCompleteOrderPaymentException() {
    order.completePayment(false);
    assertThatThrownBy(() -> order.completePayment(true))
        .isInstanceOf(IllegalOrderStateException.class);
  }

  @Test
  void testCompleteOrder() {
    order.completePayment(true);
    order.completeOrder();
    assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.COMPLETED);
  }

  @Test
  void testCompleteOrderOrderPaymentFail() {
    order.completePayment(false);

    assertThatThrownBy(() -> order.completeOrder())
        .isInstanceOf(IllegalOrderStateException.class);
  }

  @Test
  void testCompleteOrderOrderException() {
    assertThatThrownBy(() -> order.completeOrder())
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
  void testOrderCancelAfterCompleteOrder() {
    order.completePayment(true);
    order.completeOrder();
    assertThatThrownBy(() -> order.cancel())
        .isInstanceOf(IllegalOrderStateException.class);
  }
}