package aggregate.core.util;

import aggregate.core.constant.BaseSheetHeader;
import aggregate.core.constant.ColumnType;

public class CheckUtils {

  private CheckUtils() {
    super();
  }

  public static boolean isBigDecimalColumnType(BaseSheetHeader header) {
    return ColumnType.BIGDECIMAL.equals(header.getColumnType())
        || ColumnType.CURRENCY.equals(header.getColumnType())
        || ColumnType.PERCENT.equals(header.getColumnType());
  }

}
