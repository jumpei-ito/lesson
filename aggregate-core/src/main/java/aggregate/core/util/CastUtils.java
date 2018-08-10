package aggregate.core.util;

import aggregate.core.model.column.BigDecimalColumn;
import aggregate.core.model.column.Column;
import aggregate.core.model.column.DateColumn;
import aggregate.core.model.column.DayOfWeekColumn;
import aggregate.core.model.column.MonthColumn;
import aggregate.core.model.column.StringColumn;

public class CastUtils {

  private CastUtils() {
    super();
  }

  public static DateColumn toDateColumn(Column column) {
    if (!(column instanceof DateColumn)) {
      throw new RuntimeException("Fail to cast column: " + column.getClass().getName());
    }
    return (DateColumn) column;
  }

  public static BigDecimalColumn toBigDecimalColumn(Column column) {
    if (!(column instanceof BigDecimalColumn)) {
      throw new RuntimeException("Fail to cast column: " + column.getClass().getName());
    }
    return (BigDecimalColumn) column;
  }

  public static StringColumn toStringColumn(Column column) {
    if (!(column instanceof StringColumn)) {
      throw new RuntimeException("Fail to cast column: " + column.getClass().getName());
    }
    return (StringColumn) column;
  }

  public static MonthColumn toMonthColumn(Column column) {
    if (!(column instanceof MonthColumn)) {
      throw new RuntimeException("Fail to cast column: " + column.getClass().getName());
    }
    return (MonthColumn) column;
  }

  public static DayOfWeekColumn toDayOfWeekColumn(Column column) {
    if (!(column instanceof DayOfWeekColumn)) {
      throw new RuntimeException("Fail to cast column: " + column.getClass().getName());
    }
    return (DayOfWeekColumn) column;
  }

}
