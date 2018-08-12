package sales.aggregater.aggregater;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import aggregate.core.constant.SortType;
import aggregate.core.model.ColumnSet;
import aggregate.core.model.SortKey;
import aggregate.core.model.grouping.GroupingKeysBuilder;
import aggregate.core.service.BaseAggregater;
import aggregate.core.service.aggregater.Summarizer;
import aggregate.core.util.FunctionUtils;
import sales.aggregater.constant.OutputHeader;
import sales.aggregater.constant.SalesSheetHeader;

@Component
public class DayOfWeekAmountSummaryAggregater implements BaseAggregater {

  @Autowired
  private Summarizer summarizer;

  @Override
  public int getExecuteOrder() {
    return 100;
  }

  @Override
  public List<ColumnSet> aggregate(List<ColumnSet> columnSets) {
    return summarizer.execute(columnSets, getGroupingKeysBuilders(), SalesSheetHeader.AMOUNT,
        getSortKeys());
  }

  private GroupingKeysBuilder getGroupingKeysBuilders() {
    GroupingKeysBuilder builder = new GroupingKeysBuilder();
    builder.addFunctionKey(FunctionUtils.getDayOfWeekGroupingFunction(SalesSheetHeader.DATE),
        OutputHeader.DAY_OF_WEEK);
    return builder;
  }

  private List<SortKey> getSortKeys() {
    return Arrays.asList(new SortKey(OutputHeader.DAY_OF_WEEK, SortType.ASC),
        new SortKey(SalesSheetHeader.AMOUNT, SortType.DESC));
  }

}