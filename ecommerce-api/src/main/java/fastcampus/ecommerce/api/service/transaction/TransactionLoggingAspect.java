package fastcampus.ecommerce.api.service.transaction;

import fastcampus.ecommerce.api.domain.transaction.TransactionStatus;
import fastcampus.ecommerce.api.domain.transaction.TransactionType;
import fastcampus.ecommerce.api.dto.order.OrderResult;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class TransactionLoggingAspect {

  private final TransactionService transactionService;

  @Pointcut("execution(* fastcampus.ecommerce.api.service.order.OrderService.order(..))")
  public void orderCreation() {

  }

  @AfterReturning(pointcut = "orderCreation()", returning = "newOrder")
  public void logOrderCreationSuccess(Object newOrder) {
    transactionService.logTransaction(TransactionType.ORDER_CREATION, TransactionStatus.SUCCESS,
        "주문이 성공적으로 생성되었습니다. 결제를 완룔해주세요. ", (OrderResult) newOrder);
  }

  @AfterThrowing(pointcut = "orderCreation()", throwing = "exception")
  public void logOrderCreationFailure(Exception exception) {
    transactionService.logTransaction(TransactionType.ORDER_CREATION, TransactionStatus.FAILURE,
        "주문 생성 중 오류 발생 " + exception.getMessage(), null);
  }

  @Pointcut("execution(* fastcampus.ecommerce.api.service.order.OrderService.completePayment(..))")
  public void paymentCompletion() {

  }

  @AfterReturning(pointcut = "paymentCompletion()", returning = "updatedOrder")
  public void logPaymentCreationSuccess(Object updatedOrder) {
    if (((OrderResult) updatedOrder).isPaymentSuccess()) {
      transactionService.logTransaction(TransactionType.PAYMENT_COMPLETION,
          TransactionStatus.SUCCESS,
          "결제 처리가 완료되었습니다.", (OrderResult) updatedOrder);
    } else {
      transactionService.logTransaction(TransactionType.PAYMENT_COMPLETION,
          TransactionStatus.FAILURE,
          "결제 처리가 실패도이ㅓㅆ습니다. ", (OrderResult) updatedOrder);
    }

  }

  @AfterThrowing(pointcut = "paymentCompletion()", throwing = "exception")
  public void logPaymentCreationFailure(Exception exception) {
    transactionService.logTransaction(TransactionType.ORDER_CREATION, TransactionStatus.FAILURE,
        "결제 처리 중 오류 발생 " + exception.getMessage(), null);
  }

  @Pointcut("execution(* fastcampus.ecommerce.api.service.order.OrderService.completeOrder(..))")
  public void orderComplete() {

  }

  @AfterReturning(pointcut = "orderComplete()", returning = "completedOrder")
  public void logOrderCompletionSuccess(Object completedOrder) {
    transactionService.logTransaction(TransactionType.ORDER_COMPLETION, TransactionStatus.SUCCESS,
        "주문이 성공적으로 완료되었습니다. ", (OrderResult) completedOrder);
  }

  @AfterThrowing(pointcut = "orderComplete()", throwing = "exception")
  public void logOrderCompletionFailure(Exception exception) {
    transactionService.logTransaction(TransactionType.ORDER_COMPLETION, TransactionStatus.FAILURE,
        "주문 완료 중 오류 발생 " + exception.getMessage(), null);
  }

  @Pointcut("execution(* fastcampus.ecommerce.api.service.order.OrderService.cancelOrder(..))")
  public void orderCancel() {

  }

  @AfterReturning(pointcut = "orderCancel()", returning = "cancelledOrder")
  public void logOrderCancellationSuccess(Object cancelledOrder) {
    transactionService.logTransaction(TransactionType.ORDER_CANCELLATION, TransactionStatus.SUCCESS,
        "주문이 성공적으로 취소되었습니다. ", (OrderResult) cancelledOrder);
  }

  @AfterThrowing(pointcut = "orderCancel()", throwing = "exception")
  public void logOrderCancellationFailure(Exception exception) {
    transactionService.logTransaction(TransactionType.ORDER_CANCELLATION, TransactionStatus.FAILURE,
        "주문 취소 중 오류 발생 " + exception.getMessage(), null);
  }


}
