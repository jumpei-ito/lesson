package aggregate.core.service.aggregator;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import aggregate.core.constant.BaseSheetHeader;
import aggregate.core.model.AggregateKey;
import aggregate.core.model.ColumnSet;
import aggregate.core.model.SortKey;
import aggregate.core.model.grouping.GroupingKeys;
import aggregate.core.model.grouping.GroupingKeysBuilder;
import aggregate.core.service.ColumnSetConverter;
import aggregate.core.service.ColumnSetSorter;
import aggregate.core.util.CheckUtils;
import aggregate.core.util.FunctionUtils;

@Component
public class Summarizer {

  @Autowired
  private ColumnSetConverter converter;
  @Autowired
  private ColumnSetSorter sorter;

  public List<ColumnSet> execute(List<ColumnSet> columnSets, GroupingKeysBuilder builder,
      BaseSheetHeader summaryKey, List<SortKey> sortKeys) {
    Map<GroupingKeys, BigDecimal> tmpResult = columnSets.stream().collect(Collectors
        .groupingBy(FunctionUtils.getGroupingKeys(builder), getSummaryCollector(summaryKey)));
    List<ColumnSet> convertedResult = converter.convertForBigDecimal(tmpResult, summaryKey);
    return sorter.sortColumnSets(convertedResult, sortKeys);
  }

  public List<ColumnSet> execute(List<ColumnSet> columnSets, GroupingKeysBuilder builder,
      AggregateKey summaryKey, List<SortKey> sortKeys) {
    Map<GroupingKeys, BigDecimal> tmpResult =
        columnSets.stream().collect(Collectors.groupingBy(FunctionUtils.getGroupingKeys(builder),
            getSummaryCollector(summaryKey.getAggregateKey())));
    List<ColumnSet> convertedResult = converter.convertForBigDecimal(tmpResult, summaryKey.getOutputKey());
    return sorter.sortColumnSets(convertedResult, sortKeys);
  }

  private Collector<ColumnSet, ?, BigDecimal> getSummaryCollector(BaseSheetHeader summaryKey) {
    if (!CheckUtils.isBigDecimalColumnType(summaryKey)) {
      throw new RuntimeException("Target column type mast be BigDecimal: " + summaryKey.toString());
    }
    return Collectors.reducing(BigDecimal.ZERO, FunctionUtils.getBigDecimalValue(summaryKey),
        BigDecimal::add);
  }

}
