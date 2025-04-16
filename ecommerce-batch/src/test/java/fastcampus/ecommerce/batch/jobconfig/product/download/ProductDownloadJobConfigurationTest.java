package fastcampus.ecommerce.batch.jobconfig.product.download;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import fastcampus.ecommerce.batch.domain.product.Product;
import fastcampus.ecommerce.batch.jobconfig.product.BaseBatchIntegrationTest;
import fastcampus.ecommerce.batch.service.product.ProductService;
import fastcampus.ecommerce.batch.util.DateTimeUtils;
import fastcampus.ecommerce.batch.util.FileUtils;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.test.context.TestPropertySource;

@TestPropertySource(properties = {"spring.batch.job.name=productDownloadJob",
    "outputFilePath=data/products.csv"})
class ProductDownloadJobConfigurationTest extends BaseBatchIntegrationTest {

  @Value("classpath:/data/products_downloaded_expected.csv")
  private Resource expectedResource;
  File outputFile;

  @Autowired
  private ProductService productService;

  @Test
  void testJob(@Autowired @Qualifier("productDownloadJob") Job productDownloadJob)
      throws Exception {
    saveProduct();
    outputFile = FileUtils.createTempFile("products_downloaded", ".csv");
    JobParameters jobParameter = new JobParametersBuilder()
        .addJobParameter("outputFilePath",
            new JobParameter(outputFile.getPath(), String.class, false))
        .toJobParameters();
    jobLauncherTestUtils.setJob(productDownloadJob);

    JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameter);

    assertAll(
        () -> assertThat(Files.readString(Path.of(outputFile.getPath()))).isEqualTo(
            Files.readString(Path.of(expectedResource.getFile().getPath()))),
        () -> assertJobCompleted(jobExecution)
    );
  }

  @Test
  public void saveProducts() {
    saveProduct();
  }

  private void saveProduct() {
    //productId,sellerId,category,productName,salesStartDate,salesEndDate,productStatus,brand,manufacturer,salesPrice,stockQuantity,createdAt,updatedAt
    //1,1,화장품,스마트폰,2022-01-08,2026-08-14,DISCONTINUED,롯데,LG전자,81522,470,2024-09-26 14:24:21.404,2024-09-26 14:24:21.404
    //2,2,서적,소설,2022-10-24,2025-06-19,OUT_OF_STOCK,현대,스타벅스코리아,72537,1034,2024-09-28 14:24:21.404,2024-09-28 14:24:21.404
    productService.save(Product.of("1", 1L, "화장품",
        "스마트폰", LocalDate.of(2022, 1, 8),
        LocalDate.of(2026, 8, 14), "DISCONTINUED", "롯데",
        "LG전자", 81522, 470,
        DateTimeUtils.toLocalDateTime("2024-09-26 14:24:21.404"),
        DateTimeUtils.toLocalDateTime("2024-09-26 14:24:21.404")));
    productService.save(Product.of("2", 2L, "서적",
        "소설", LocalDate.of(2022, 10, 24),
        LocalDate.of(2025, 6, 19), "OUT_OF_STOCK", "현대",
        "스타벅스코리아", 72537, 1034,
        DateTimeUtils.toLocalDateTime("2024-09-28 14:24:21.404"),
        DateTimeUtils.toLocalDateTime("2024-09-28 14:24:21.404")));
  }

  private JobParameters jobParameters() throws IOException {
    return new JobParametersBuilder()
        .addJobParameter("inputFilePath",
            new JobParameter<>(outputFile.getPath(), String.class, false))
        .addJobParameter("gridSize", new JobParameter<>(3, Integer.class, false))
        .toJobParameters();
  }

}