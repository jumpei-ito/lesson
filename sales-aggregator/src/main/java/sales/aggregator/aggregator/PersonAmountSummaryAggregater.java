package sales.aggregator.aggregator;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import aggregate.core.constant.SortType;
import aggregate.core.model.ColumnSet;
import aggregate.core.model.SortKey;
import aggregate.core.model.grouping.GroupingKeysBuilder;
import aggregate.core.service.BaseAggregater;
import aggregate.core.service.aggregator.Summarizer;
import sales.aggregator.constant.SalesSheetHeader;

@Component
public class PersonAmountSummaryAggregater implements BaseAggregater {

  @Autowired
  private Summarizer summarizer;

  @Override
  public int getExecuteOrder() {
    return 300;
  }

  @Override
  public List<ColumnSet> aggregate(List<ColumnSet> columnSets) {
    return summarizer.execute(columnSets, getGroupingKeysBuilders(), SalesSheetHeader.AMOUNT,
        getSortKeys());
  }

  private GroupingKeysBuilder getGroupingKeysBuilders() {
    GroupingKeysBuilder builder = new GroupingKeysBuilder();
    builder.addHeaderKey(SalesSheetHeader.PERSON);
    builder.addHeaderKey(SalesSheetHeader.ITEM_CODE);
    return builder;
  }

  private List<SortKey> getSortKeys() {
    return Arrays.asList(new SortKey(SalesSheetHeader.PERSON, SortType.DESC),
        new SortKey(SalesSheetHeader.ITEM_CODE, SortType.ASC));
  }

}
