package fastcampus.ecommerce.batch.util;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class DateTimeUtilsTest {

  @Test
  void toLocalDateTime() {
    String dateTime = "2024-09-28 14:24:21.404";

    LocalDateTime localDateTime = DateTimeUtils.toLocalDateTime(dateTime);

    assertThat(localDateTime).isEqualTo(LocalDateTime.of(2024, 9, 28, 14,
        24, 21, 404000000));
  }
}