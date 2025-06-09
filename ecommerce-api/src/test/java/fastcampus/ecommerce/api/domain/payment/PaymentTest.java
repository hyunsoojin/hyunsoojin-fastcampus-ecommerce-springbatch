package fastcampus.ecommerce.api.domain.payment;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PaymentTest {

  Payment payment;

  @BeforeEach
  void setUp() {
    payment = getPayment();
  }

  @Test
  void testPaymentPending() {
    assertThat(payment.getPaymentStatus()).isEqualTo(PaymentStatus.PENDING);
  }

  @Test
  void testPaymentComplete() {
    payment.complete();
    assertThat(payment.getPaymentStatus()).isEqualTo(PaymentStatus.COMPLETED);
  }

  @Test
  void testPaymentCompleteException() {
    payment.complete();
    assertThatThrownBy(payment::complete)
        .isInstanceOf(IllegalPaymentStateException.class);
  }

  @Test
  void testPaymentFail() {
    payment.fail();
    assertThat(payment.getPaymentStatus()).isEqualTo(PaymentStatus.FAILED);
  }

  private static Payment getPayment() {
    return Payment.createPayment(PaymentMethod.CREDIT_CARD, 1000, null);
  }

  @Test
  void testPaymentFailException() {
    payment.complete();
    assertThatThrownBy(payment::fail)
        .isInstanceOf(IllegalPaymentStateException.class);
  }

  @Test
  void testPaymentCancelAfterComplete() {
    payment.complete();
    payment.cancel();
    assertThat(payment.getPaymentStatus()).isEqualTo(PaymentStatus.REFUNDED);
  }

  @Test
  void testPaymentCancelAfterPending() {
    payment.cancel();
    assertThat(payment.getPaymentStatus()).isEqualTo(PaymentStatus.CANCELLED);
  }

  @Test
  void testPaymentCancelAfterFail() {
    payment.cancel();
    assertThat(payment.getPaymentStatus()).isEqualTo(PaymentStatus.CANCELLED);
  }

  @Test
  void testPaymentCancelAfterRefund() {
    payment.complete();
    payment.cancel();
    assertThatThrownBy(payment::cancel).
        isInstanceOf(IllegalPaymentStateException.class);
  }

  @Test
  void testPaymentCancelAfterCancel() {
    payment.fail();
    payment.cancel();
    assertThatThrownBy(payment::cancel).
        isInstanceOf(IllegalPaymentStateException.class);
  }

}