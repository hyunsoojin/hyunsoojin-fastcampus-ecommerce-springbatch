package fastcampus.ecommerce.batch.util;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class FileUtilsTest {

  @Value("classpath:/data/products_for_upload.csv")
  private Resource csvResource;

  @TempDir
  Path tempDir;

  @Test
  void splitCsv() throws IOException {
    List<File> files = FileUtils.splitCsv(csvResource.getFile(), 2);

    assertThat(files).hasSize(2);
  }

  @Test
  void createTempFile() throws IOException {
    File file = FileUtils.createTempFile("prefix", "suffix");
    assertThat(file.exists()).isTrue();
  }

  @Test
  void testMergeFiles() throws IOException {
    File file1 = createFile("filename1.csv", "Content 1\n");
    File file2 = createFile("filename2.csv", "Content 2\n");
    File file3 = createFile("filename3.csv", "Content 3\n");
    File outputFile = new File(tempDir.toFile(), "output.txt");
    String header = "content";

    FileUtils.mergeFile(header, List.of(file1, file2, file3), outputFile);
    assertThat(Files.readAllLines(outputFile.toPath())).containsExactly("content", "Content1",
        "Content2", "Content3");

  }

  private File createFile(String fileName, String content) throws IOException {
    File file = new File(tempDir.toFile(), fileName);
    Files.write(file.toPath(), content.getBytes());
    return file;
  }

}