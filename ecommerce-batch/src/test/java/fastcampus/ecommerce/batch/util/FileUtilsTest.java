package fastcampus.ecommerce.batch.util;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class FileUtilsTest {

  @Value("classpath:/data/products_for_upload.csv")
  private Resource csvResource;

  @Test
  void splitCsv() throws IOException {
    List<File> files = FileUtils.splitCsv(csvResource.getFile(), 2);

    assertThat(files).hasSize(2);
  }
}