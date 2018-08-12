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
import aggregate.core.service.aggregater.Counter;
import sales.aggregater.constant.OutputHeader;
import sales.aggregater.constant.SalesSheetHeader;

@Component
public class ItemCountAggregater implements BaseAggregater {

  @Autowired
  private Counter counter;

  @Override
  public int getExecuteOrder() {
    return 125;
  }

  @Override
  public List<ColumnSet> aggregate(List<ColumnSet> columnSets) {
    return counter.execute(columnSets, getGroupingKeysBuilders(), OutputHeader.COUNT,
        getSortKeys());
  }

  private GroupingKeysBuilder getGroupingKeysBuilders() {
    GroupingKeysBuilder builder = new GroupingKeysBuilder();
    builder.addHeaderKey(SalesSheetHeader.PERSON);
    builder.addHeaderKey(SalesSheetHeader.ITEM_CODE);
    return builder;
  }

  private List<SortKey> getSortKeys() {
    return Arrays.asList(new SortKey(SalesSheetHeader.PERSON, SortType.ASC),
        new SortKey(SalesSheetHeader.ITEM_CODE, SortType.ASC));
  }

}