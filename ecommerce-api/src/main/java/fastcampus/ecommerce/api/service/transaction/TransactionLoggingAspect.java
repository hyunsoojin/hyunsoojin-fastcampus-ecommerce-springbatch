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
        "주문이 성공적으로 생성되었습니다. 결제를 오나료해주세요. ", (OrderResult) newOrder);
  }

  @AfterThrowing(pointcut = "orderCreation()", throwing = "exception")
  public void logOrderCreationFailure(Exception exception) {
    transactionService.logTransaction(TransactionType.ORDER_CREATION, TransactionStatus.FAILURE,
        "주문 생성 중 오류 발생 " + exception.getMessage(), null);
  }
}
