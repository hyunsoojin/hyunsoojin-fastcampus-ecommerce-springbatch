package fastcampus.ecommerce.batch.service.monitoring;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class BatchJobExecutionListener implements JobExecutionListener {

  @Override
  public void beforeJob(JobExecution jobExecution) {
    log.info("listener: before job");
  }

  @Override
  public void afterJob(JobExecution jobExecution) {
    log.info("listener: after job {}", jobExecution.getExecutionContext());
  }
}
