package aggregate.core.util;

import java.util.List;

import aggregate.core.constant.BaseSheetHeader;
import aggregate.core.constant.ColumnType;
import aggregate.core.model.ColumnSet;

public class CheckUtils {

  private CheckUtils() {
    super();
  }

  public static boolean isBigDecimalColumnType(BaseSheetHeader header) {
    return ColumnType.BIGDECIMAL.equals(header.getColumnType())
        || ColumnType.CURRENCY.equals(header.getColumnType())
        || ColumnType.PERCENT.equals(header.getColumnType());
  }

  public static boolean containsKeys(ColumnSet columnSet, List<BaseSheetHeader> headers) {
    for (BaseSheetHeader header : headers) {
      if (!columnSet.containsHeader(header)) {
        return false;
      }
    }
    return true;
  }
}
