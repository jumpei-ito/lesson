package aggregate.core.util;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.Month;
import java.util.function.Function;

import aggregate.core.constant.BaseSheetHeader;
import aggregate.core.model.ColumnSet;
import aggregate.core.model.grouping.GroupingKeys;
import aggregate.core.model.grouping.GroupingKeysBuilder;

public class FunctionUtils {

  private FunctionUtils() {
    super();
  }

  public static Function<ColumnSet, GroupingKeys> getGroupingKeys(GroupingKeysBuilder builder) {
    return columnSet -> builder.build(columnSet);
  }

  public static Function<ColumnSet, BigDecimal> getBigDecimalValue(BaseSheetHeader summaryKey) {
    return columnSet -> CastUtils.toBigDecimalColumn(columnSet.getColumn(summaryKey)).getValue();
  }

  public static Function<ColumnSet, String> getStringGroupingKey(BaseSheetHeader header) {
    return columnSet -> columnSet.getColumn(header).getValueAsString();
  }

  public static Function<ColumnSet, DayOfWeek> getDayOfWeekGroupingFunction(
      BaseSheetHeader header) {
    return columnSet -> CastUtils.toDateColumn(columnSet.getColumn(header)).getValue()
        .getDayOfWeek();
  }

  public static Function<ColumnSet, Month> getMonthGroupingFunction(BaseSheetHeader header) {
    return columnSet -> CastUtils.toDateColumn(columnSet.getColumn(header)).getValue().getMonth();
  }

}
