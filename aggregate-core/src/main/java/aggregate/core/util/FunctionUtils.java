package aggregate.core.util;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.Month;
import java.util.function.Function;
import aggregate.core.constant.BaseSheetHeader;
import aggregate.core.model.ColumnSet;
import aggregate.core.model.grouping.GroupingKeys;
import aggregate.core.model.grouping.GroupingKeysBuilder;

/**
 * Util class has Getter of common functions.
 */
public class FunctionUtils {

  /**
   * Constructor
   */
  private FunctionUtils() {
    super();
  }

  /**
   * Getter of function to get GropingKeys from argument builder with ColumnSet.
   *
   * @param builder Builder of grouping key
   * @return Function to get GropingKeys
   */
  public static Function<ColumnSet, GroupingKeys> getGroupingKeys(GroupingKeysBuilder builder) {
    return columnSet -> builder.build(columnSet);
  }

  /**
   * Getter of function to get BigDecimal value from argument header column.
   *
   * @param header Column header
   * @return Function to get BigDecimal value
   */
  public static Function<ColumnSet, BigDecimal> getBigDecimalValue(BaseSheetHeader header) {
    return columnSet -> CastUtils.toBigDecimalColumn(columnSet.getColumn(header)).getValue();
  }

  /**
   * Getter of function to get day of week value from argument header column.
   *
   * @param header Column header
   * @return Function to get day of week value
   */
  public static Function<ColumnSet, DayOfWeek> getDayOfWeekValue(BaseSheetHeader header) {
    return columnSet -> CastUtils.toDateColumn(columnSet.getColumn(header)).getValue()
        .getDayOfWeek();
  }

  /**
   * Getter of function to get month value from argument header column.
   *
   * @param header Column header
   * @return Function to get month value
   */
  public static Function<ColumnSet, Month> getMonthValue(BaseSheetHeader header) {
    return columnSet -> CastUtils.toDateColumn(columnSet.getColumn(header)).getValue().getMonth();
  }

}
