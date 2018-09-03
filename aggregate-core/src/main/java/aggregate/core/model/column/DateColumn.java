package aggregate.core.model.column;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import aggregate.core.util.CastUtils;

/**
 * Column Class for date value.
 */
public class DateColumn extends Column {

  private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
  private LocalDate value;

  /**
   * Constructor
   *
   * @param no Column number
   * @param value Column value of date as String（yyyy/MM/dd）.
   */
  public DateColumn(int no, String value) {
    super(no, value);
    this.value = LocalDate.parse(value, formatter);
  }

  /**
   * Constructor
   *
   * @param no Column number
   * @param value Column value of date.
   */
  public DateColumn(int no, LocalDate value) {
    super(no, value.format(formatter));
    this.value = value;
  }

  /**
   * Getter of value.
   *
   * @return Column value
   */
  public LocalDate getValue() {
    return value;
  }

  @Override
  public int compareTo(Column column) {
    // TODO: null handling
    return value.compareTo(CastUtils.toDateColumn(column).getValue());
  }

  @Override
  public boolean equals(Column column) {
    // TODO: null handling
    return value.equals(CastUtils.toDateColumn(column).getValue());
  }

  @Override
  public Column clone() {
    return new DateColumn(getNo(), value);
  }

}
