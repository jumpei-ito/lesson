package aggregate.core.service.aggregator;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import aggregate.core.constant.BaseSheetHeader;
import aggregate.core.constant.ColumnType;
import aggregate.core.exception.MissingColumnTypeException;
import aggregate.core.model.AggregateKey;
import aggregate.core.model.ColumnSet;
import aggregate.core.model.SortKey;
import aggregate.core.model.grouping.GroupingKeys;
import aggregate.core.model.grouping.GroupingKeysBuilder;
import aggregate.core.service.ColumnSetConverter;
import aggregate.core.service.ColumnSetSorter;
import aggregate.core.util.CheckUtils;
import aggregate.core.util.FunctionUtils;

/**
 * Aggregator class to summarize numeric column values.
 */
@Component
public class Summarizer {

  @Autowired
  private ColumnSetConverter converter;
  @Autowired
  private ColumnSetSorter sorter;

  /**
   * Summarizes one column by some grouping keys and sorts by argument keys.
   *
   * @param columnSets List of ColumnSet to be aggregated
   * @param builder Builder of some grouping keys
   * @param summaryKey Column header to be summarized
   * @param sortKeys Sort keys
   * @return Aggregated ColumnSet list
   */
  public List<ColumnSet> execute(List<ColumnSet> columnSets, GroupingKeysBuilder builder,
      BaseSheetHeader summaryKey, List<SortKey> sortKeys) {
    // aggregate
    Map<GroupingKeys, BigDecimal> tmpResult = columnSets.stream().collect(Collectors
        .groupingBy(FunctionUtils.getGroupingKeys(builder), getSummaryCollector(summaryKey)));
    // convert to columnSet
    List<ColumnSet> convertedResult = converter.convert(tmpResult, summaryKey);
    // sort columnSet
    return sorter.sortColumnSets(convertedResult, sortKeys);
  }

  /**
   * Summarizes one column by some grouping keys and sorts by argument keys.<br>
   * Add summarized column as output header of summaryKey to result ColumnSet.
   *
   * @param columnSets List of ColumnSet to be aggregated
   * @param builder Builder of some grouping keys
   * @param summaryKey Conditions about summary and summarized column
   * @param sortKeys Sort keys
   * @return Aggregated ColumnSet list
   */
  public List<ColumnSet> execute(List<ColumnSet> columnSets, GroupingKeysBuilder builder,
      AggregateKey summaryKey, List<SortKey> sortKeys) {
    // aggregate
    Map<GroupingKeys, BigDecimal> tmpResult =
        columnSets.stream().collect(Collectors.groupingBy(FunctionUtils.getGroupingKeys(builder),
            getSummaryCollector(summaryKey.getAggregateKey())));
    // convert to columnSet
    List<ColumnSet> convertedResult = converter.convert(tmpResult, summaryKey.getOutputKey());
    // sort columnSet
    return sorter.sortColumnSets(convertedResult, sortKeys);
  }

  private Collector<ColumnSet, ?, BigDecimal> getSummaryCollector(BaseSheetHeader summaryKey) {
    if (!CheckUtils.isBigDecimalColumnType(summaryKey)) {
      throw new MissingColumnTypeException(ColumnType.BIGDECIMAL, summaryKey.getColumnType());
    }
    return Collectors.reducing(BigDecimal.ZERO, FunctionUtils.getBigDecimalValue(summaryKey),
        BigDecimal::add);
  }

}
