package fastcampus.ecommerce.batch.jobconfig.product.upload;

import fastcampus.ecommerce.batch.BatchApplication;
import java.io.IOException;
import javax.sql.DataSource;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@Sql("/sql/schema.sql")
@SpringBatchTest
@SpringJUnitConfig(classes = {BatchApplication.class})
@TestPropertySource(properties = {"spring.batch.job.name=productUploadJob"})
class ProductUploadJobConfigurationTest {

  @Autowired
  private JobLauncherTestUtils jobLauncherTestUtils;

  private JdbcTemplate jdbcTemplate;

  @Autowired
  public void setDateSource(DataSource dataSource) {
    jdbcTemplate = new JdbcTemplate(dataSource);
  }

  @Value("classpath:/data/products_for_upload.csv")
  private Resource input;

  @Test
  void testJob(@Autowired Job productUploadJob) throws IOException {
    JobParameters jobParameter = new JobParametersBuilder()
        .addJobParameter("inputFilePath",
            new JobParameter<>(input.getFile().getPath(), String.class, false))
        .toJobParameters();

  }


}