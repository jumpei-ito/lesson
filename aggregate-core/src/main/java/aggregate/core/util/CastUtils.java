package aggregate.core.util;

import aggregate.core.model.column.BigDecimalColumn;
import aggregate.core.model.column.Column;
import aggregate.core.model.column.DateColumn;
import aggregate.core.model.column.DayOfWeekColumn;
import aggregate.core.model.column.MonthColumn;
import aggregate.core.model.column.StringColumn;

/**
 * Util class has cast column methods.
 */
public class CastUtils {

  /**
   * Constructor
   */
  private CastUtils() {
    super();
  }

  /**
   * Casts argument Column to DateColumn if possible.
   *
   * @param column Column to be casted
   * @return DateColumn
   */
  public static DateColumn toDateColumn(Column column) {
    if (!(column instanceof DateColumn)) {
      throw new RuntimeException("Fail to cast column: " + column.getClass().getName());
    }
    return (DateColumn) column;
  }

  /**
   * Casts argument Column to BigDecimalColumn if possible.
   *
   * @param column Column to be casted
   * @return BigDecimalColumn
   */
  public static BigDecimalColumn toBigDecimalColumn(Column column) {
    if (!(column instanceof BigDecimalColumn)) {
      throw new RuntimeException("Fail to cast column: " + column.getClass().getName());
    }
    return (BigDecimalColumn) column;
  }

  /**
   * Casts argument Column to StringColumn if possible.
   *
   * @param column Column to be casted
   * @return StringColumn
   */
  public static StringColumn toStringColumn(Column column) {
    if (!(column instanceof StringColumn)) {
      throw new RuntimeException("Fail to cast column: " + column.getClass().getName());
    }
    return (StringColumn) column;
  }

  /**
   * Casts argument Column to MonthColumn if possible.
   *
   * @param column Column to be casted
   * @return MonthColumn
   */
  public static MonthColumn toMonthColumn(Column column) {
    if (!(column instanceof MonthColumn)) {
      throw new RuntimeException("Fail to cast column: " + column.getClass().getName());
    }
    return (MonthColumn) column;
  }

  /**
   * Casts argument Column to DayOfWeekColumn if possible.
   *
   * @param column Column to be casted
   * @return DayOfWeekColumn
   */
  public static DayOfWeekColumn toDayOfWeekColumn(Column column) {
    if (!(column instanceof DayOfWeekColumn)) {
      throw new RuntimeException("Fail to cast column: " + column.getClass().getName());
    }
    return (DayOfWeekColumn) column;
  }

}
