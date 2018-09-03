package aggregate.core.util;

import aggregate.core.constant.BaseSheetHeader;
import aggregate.core.constant.ColumnType;

/**
 * Util class has common check methods.
 */
public class CheckUtils {

  /**
   * Constructor
   */
  private CheckUtils() {
    super();
  }

  /**
   * Checks whether argument column type is numeric or else.
   *
   * @param header Column header
   * @return true if column type is numeric, false otherwise.
   */
  public static boolean isBigDecimalColumnType(BaseSheetHeader header) {
    return ColumnType.BIGDECIMAL.equals(header.getColumnType())
        || ColumnType.CURRENCY.equals(header.getColumnType())
        || ColumnType.PERCENT.equals(header.getColumnType());
  }

}
