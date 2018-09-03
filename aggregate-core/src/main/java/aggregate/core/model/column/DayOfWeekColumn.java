package aggregate.core.model.column;

import java.time.DayOfWeek;
import java.time.format.TextStyle;
import java.util.Locale;
import aggregate.core.util.CastUtils;

/**
 * Column Class for day of week value.
 */
public class DayOfWeekColumn extends Column {

  private DayOfWeek value;

  /**
   * Constructor
   *
   * @param no Column number
   * @param value Column value of day of week as String
   */
  public DayOfWeekColumn(int no, String value) {
    super(no, getDisplayName(DayOfWeek.valueOf(value)));
    this.value = DayOfWeek.valueOf(value);
  }

  /**
   * Constructor
   *
   * @param no Column number
   * @param value Column value of day of week
   */
  public DayOfWeekColumn(int no, DayOfWeek value) {
    super(no, getDisplayName(value));
    this.value = value;
  }

  private static String getDisplayName(DayOfWeek value) {
    return value.getDisplayName(TextStyle.FULL, Locale.JAPANESE);
  }

  /**
   * Getter of value.
   *
   * @return Column value
   */
  public DayOfWeek getValue() {
    return value;
  }

  @Override
  public int compareTo(Column column) {
    // TODO: null handling
    return value.compareTo(CastUtils.toDayOfWeekColumn(column).getValue());
  }

  @Override
  public boolean equals(Column column) {
    // TODO: null handling
    return value.equals(CastUtils.toDayOfWeekColumn(column).getValue());
  }

  @Override
  public Column clone() {
    return new DayOfWeekColumn(getNo(), value);
  }

}
