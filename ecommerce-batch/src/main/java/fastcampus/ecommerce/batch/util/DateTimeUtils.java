package fastcampus.ecommerce.batch.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtils {

  private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

  private static final DateTimeFormatter dateTimeFormmater = DateTimeFormatter.ofPattern(
      "yyyy-MM-dd HH:mm:ss.SSS");

  public static LocalDate toLocalDate(String date) {
    return LocalDate.parse(date, dateFormatter);
  }

  public static LocalDateTime toLocalDateTime(String dateTime) {
    return LocalDateTime.parse(dateTime, dateTimeFormmater);
  }

  public static String toString(LocalDateTime dateTime) {
    return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS").format(dateTime);
  }

}
