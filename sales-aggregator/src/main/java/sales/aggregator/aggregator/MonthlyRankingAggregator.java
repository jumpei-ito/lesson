package sales.aggregator.aggregator;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import aggregate.core.constant.SortType;
import aggregate.core.model.AggregateKey;
import aggregate.core.model.ColumnSet;
import aggregate.core.model.SortKey;
import aggregate.core.model.grouping.GroupingKeysBuilder;
import aggregate.core.service.BaseAggregator;
import aggregate.core.service.aggregator.Summarizer;
import aggregate.core.util.FunctionUtils;
import sales.aggregator.constant.OutputHeader;
import sales.aggregator.constant.SalesSheetHeader;

@Component
public class MonthlyRankingAggregator implements BaseAggregator {

  @Autowired
  private Summarizer summarizer;

  @Override
  public int getExecuteOrder() {
    return 150;
  }

  @Override
  public List<ColumnSet> aggregate(List<ColumnSet> columnSets) {
    List<ColumnSet> result =
        summarizer.execute(columnSets, getGroupingKeys(), getAggregateKey(), getSortKeys());
    return result;
  }

  private GroupingKeysBuilder getGroupingKeys() {
    GroupingKeysBuilder builder = new GroupingKeysBuilder();
    builder.addFunctionKey(FunctionUtils.getMonthValue(SalesSheetHeader.DATE),
        OutputHeader.MONTH);
    builder.addHeaderKey(SalesSheetHeader.PERSON);
    return builder;
  }

  private AggregateKey getAggregateKey() {
    return new AggregateKey(SalesSheetHeader.AMOUNT, OutputHeader.PERSONAL_TOTAL_AMONT);
  }

  private List<SortKey> getSortKeys() {
    return Arrays.asList(new SortKey(OutputHeader.MONTH, SortType.ASC),
        new SortKey(SalesSheetHeader.PERSON, SortType.ASC));
  }

}
