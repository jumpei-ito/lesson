package aggregate.core.model.column;

import java.time.Month;
import java.time.format.TextStyle;
import java.util.Locale;

import aggregate.core.util.CastUtils;

public class MonthColumn extends Column {

  private Month value;

  public MonthColumn(int no, String value) {
    super(no, Month.valueOf(value).getDisplayName(TextStyle.FULL, Locale.JAPANESE));
    this.value = Month.valueOf(value);
  }

  public MonthColumn(int no, Month value) {
    super(no, value.getDisplayName(TextStyle.FULL, Locale.JAPANESE));
    this.value = value;
  }

  public Month getValue() {
    return value;
  }

  @Override
  public int compareTo(Column column) {
    // TODO: null handling
    return value.compareTo(CastUtils.toMonthColumn(column).getValue());
  }

  @Override
  public boolean equals(Column column) {
    // TODO: null handling
    return value.equals(CastUtils.toMonthColumn(column).getValue());
  }

  @Override
  public Column clone() {
    return new MonthColumn(getNo(), value);
  }

}
